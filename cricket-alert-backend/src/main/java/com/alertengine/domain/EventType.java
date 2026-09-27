package com.alertengine.domain;

/** Every event type belongs to exactly one sport, so a MatchEvent can never carry a mismatched sport. */
public enum EventType {
    WICKET(SportType.CRICKET),
    BOUNDARY(SportType.CRICKET),
    MILESTONE(SportType.CRICKET),
    GOAL(SportType.FOOTBALL),
    RED_CARD(SportType.FOOTBALL),
    MATCH_POINT(SportType.TENNIS),
    SCORING_RUN(SportType.BASKETBALL);

    private final SportType sport;

    EventType(SportType sport) {
        this.sport = sport;
    }

    public SportType sport() {
        return sport;
    }
}
