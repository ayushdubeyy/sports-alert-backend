package com.alertengine.domain;

/**
 * Strategy: decides whether an event is alert-worthy and how the alert reads.
 * New sports or conditions = new implementation, no change to the engine.
 */
public interface AlertRule {

    SportType sport();

    boolean matches(MatchEvent event);

    String describe(MatchEvent event);
}
