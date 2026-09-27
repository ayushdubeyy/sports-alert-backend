package com.alertengine.notify;

import com.alertengine.domain.Alert;
import com.alertengine.domain.NotificationChannel;
import com.alertengine.domain.Subscriber;

/** Stub notifier that prints instead of sending. */
public class ConsoleNotifier implements Notifier {

    private final NotificationChannel channel;

    public ConsoleNotifier(NotificationChannel channel) {
        this.channel = channel;
    }

    @Override
    public NotificationChannel channel() {
        return channel;
    }

    @Override
    public void send(Subscriber subscriber, Alert alert) {
        System.out.println("[" + channel + " -> " + subscriber.name() + "] " + alert.message());
    }
}
