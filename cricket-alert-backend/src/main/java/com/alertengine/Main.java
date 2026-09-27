package com.alertengine;

import com.alertengine.domain.*;
import com.alertengine.engine.AlertEngine;
import com.alertengine.notify.ConsoleNotifier;
import com.alertengine.notify.NotifierRegistry;
import com.alertengine.rules.RuleFactory;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        NotifierRegistry notifiers = new NotifierRegistry();
        for (NotificationChannel ch : NotificationChannel.values()) {
            notifiers.register(new ConsoleNotifier(ch));
        }
        AlertEngine engine = new AlertEngine(notifiers);
        RuleFactory rules = new RuleFactory();

        Subscriber ayush = new Subscriber("u1", "Ayush");
        Subscriber sam = new Subscriber("u2", "Sam");

        engine.subscribe(Subscription.of(ayush, "IND-AUS-1", rules.create("CRICKET_WICKET", Map.of()), NotificationChannel.PUSH));
        engine.subscribe(Subscription.of(ayush, null, rules.create("CRICKET_MILESTONE", Map.of("minRuns", "50")), NotificationChannel.EMAIL));
        engine.subscribe(Subscription.of(sam, null, rules.create("FOOTBALL_GOAL", Map.of("team", "Mumbai City")), NotificationChannel.PUSH));
        engine.subscribe(Subscription.of(sam, null, rules.create("TENNIS_MATCH_POINT", Map.of()), NotificationChannel.SMS));
        engine.subscribe(Subscription.of(sam, null, rules.create("BASKETBALL_SCORING_RUN", Map.of("minPoints", "10")), NotificationChannel.PUSH));

        int total = 0;
        total += engine.publish(ev("IND-AUS-1", EventType.WICKET, "player", "Warner", "runs", "34")).size();
        total += engine.publish(ev("IND-AUS-2", EventType.WICKET, "player", "Smith", "runs", "12")).size();   // other match -> no alert
        total += engine.publish(ev("IND-AUS-1", EventType.MILESTONE, "player", "Kohli", "runs", "52")).size();
        total += engine.publish(ev("IND-AUS-1", EventType.BOUNDARY, "player", "Kohli", "runs", "4")).size();  // no rule -> no alert
        total += engine.publish(ev("ISL-M12", EventType.GOAL, "player", "Bipin", "team", "Mumbai City", "minute", "67")).size();
        total += engine.publish(ev("ISL-M12", EventType.GOAL, "player", "Coro", "team", "Goa", "minute", "80")).size(); // other team -> no alert
        total += engine.publish(ev("WIM-F", EventType.MATCH_POINT, "player", "Alcaraz", "score", "5-4")).size();
        total += engine.publish(ev("NBA-G7", EventType.SCORING_RUN, "team", "Celtics", "points", "12")).size();

        System.out.println("\nTotal alerts dispatched: " + total + " (expected 5)");
    }

    private static MatchEvent ev(String matchId, EventType type, String... kv) {
        Map<String, String> attrs = new HashMap<>();
        for (int i = 0; i + 1 < kv.length; i += 2) {
            attrs.put(kv[i], kv[i + 1]);
        }
        return MatchEvent.of(matchId, type, attrs);
    }
}
