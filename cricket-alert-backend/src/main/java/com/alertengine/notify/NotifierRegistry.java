package com.alertengine.notify;

import com.alertengine.domain.NotificationChannel;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class NotifierRegistry {

    private final Map<NotificationChannel, Notifier> notifiers = new ConcurrentHashMap<>();

    public void register(Notifier notifier) {
        notifiers.put(notifier.channel(), notifier);
    }

    public Optional<Notifier> find(NotificationChannel channel) {
        return Optional.ofNullable(notifiers.get(channel));
    }
}
