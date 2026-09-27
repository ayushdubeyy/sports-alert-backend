package com.alertengine.notify;

import com.alertengine.domain.Alert;
import com.alertengine.domain.NotificationChannel;
import com.alertengine.domain.Subscriber;

/** Delivery strategy for one channel. Real email/push/SMS implementations plug in here later. */
public interface Notifier {

    NotificationChannel channel();

    void send(Subscriber subscriber, Alert alert);
}
