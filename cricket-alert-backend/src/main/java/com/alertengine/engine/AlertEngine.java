package com.alertengine.engine;

import com.alertengine.domain.*;
import com.alertengine.notify.Notifier;
import com.alertengine.notify.NotifierRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Observer-style hub: event sources publish MatchEvents, subscriptions are the observers.
 * Subscriptions are indexed by sport so a football goal never scans tennis subscriptions.
 */
public class AlertEngine {

    private final Map<SportType, List<Subscription>> bySport = new ConcurrentHashMap<>();
    private final Map<String, Subscription> byId = new ConcurrentHashMap<>();
    private final NotifierRegistry notifiers;

    public AlertEngine(NotifierRegistry notifiers) {
        this.notifiers = notifiers;
    }

    public void subscribe(Subscription subscription) {
        byId.put(subscription.id(), subscription);
        bySport.computeIfAbsent(subscription.rule().sport(), k -> new CopyOnWriteArrayList<>())
                .add(subscription);
    }

    public boolean unsubscribe(String subscriptionId) {
        Subscription curr = byId.remove(subscriptionId);
        if (curr == null) return false;
        List<Subscription> list = bySport.get(curr.rule().sport());
        if (list != null) list.remove(curr);
        return true;
    }

    /** Returns the alerts that were dispatched (handy for tests and for persistence in a later phase). */
    public List<Alert> publish(MatchEvent event) {
        List<Alert> res = new ArrayList<>();
        for (Subscription curr : bySport.getOrDefault(event.sport(), List.of())) {
            if (!curr.appliesTo(event)) continue;

            Alert alert = Alert.create(curr.subscriber().id(), event, curr.rule().describe(event));
            Optional<Notifier> notifier = notifiers.find(curr.channel());
            if (notifier.isEmpty()) {
                System.err.println("No notifier registered for " + curr.channel());
                continue;
            }
            try {
                notifier.get().send(curr.subscriber(), alert);
                res.add(alert);
            } catch (RuntimeException e) {
                // one failing delivery must not block the other subscribers
                System.err.println("Delivery failed for " + curr.subscriber().name() + ": " + e.getMessage());
            }
        }
        return res;
    }
}
