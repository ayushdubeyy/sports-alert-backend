package com.alertengine.domain;

import java.time.Instant;
import java.util.UUID;

public record Alert(String id, String subscriberId, MatchEvent event, String message, Instant createdAt) {

    public static Alert create(String subscriberId, MatchEvent event, String message) {
        return new Alert(UUID.randomUUID().toString(), subscriberId, event, message, Instant.now());
    }
}
