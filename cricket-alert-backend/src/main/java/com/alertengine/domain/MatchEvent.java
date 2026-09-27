package com.alertengine.domain;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/** Immutable fact that something happened in a match. Sport-specific details live in attributes. */
public record MatchEvent(String matchId, EventType type, Map<String, String> attributes, Instant occurredAt) {

    public MatchEvent {
        Objects.requireNonNull(matchId, "matchId");
        Objects.requireNonNull(type, "type");
        attributes = attributes == null ? Map.of() : Map.copyOf(attributes);
        occurredAt = occurredAt == null ? Instant.now() : occurredAt;
    }

    public static MatchEvent of(String matchId, EventType type, Map<String, String> attributes) {
        return new MatchEvent(matchId, type, attributes, Instant.now());
    }

    public SportType sport() {
        return type.sport();
    }

    public String attr(String key) {
        return attributes.getOrDefault(key, "");
    }

    public int intAttr(String key, int fallback) {
        try {
            return Integer.parseInt(attributes.get(key));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
