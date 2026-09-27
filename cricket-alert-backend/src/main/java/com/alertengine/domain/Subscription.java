package com.alertengine.domain;

import java.util.Objects;
import java.util.UUID;

/** "Tell this subscriber, via this channel, when this rule fires (optionally only for one match)." */
public record Subscription(String id, Subscriber subscriber, String matchId, AlertRule rule,
                           NotificationChannel channel) {

    public Subscription {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(subscriber, "subscriber");
        Objects.requireNonNull(rule, "rule");
        Objects.requireNonNull(channel, "channel");
    }

    /** matchId == null means "all matches of this rule's sport". */
    public static Subscription of(Subscriber subscriber, String matchId, AlertRule rule,
                                  NotificationChannel channel) {
        return new Subscription(UUID.randomUUID().toString(), subscriber, matchId, rule, channel);
    }

    public boolean appliesTo(MatchEvent event) {
        boolean matchOk = matchId == null || matchId.equals(event.matchId());
        return matchOk && rule.matches(event);
    }
}
