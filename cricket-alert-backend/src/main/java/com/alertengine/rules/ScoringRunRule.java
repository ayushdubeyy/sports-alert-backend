package com.alertengine.rules;

import com.alertengine.domain.*;

public class ScoringRunRule implements AlertRule {

    private final int minPoints;

    public ScoringRunRule(int minPoints) {
        this.minPoints = minPoints;
    }

    @Override
    public SportType sport() {
        return SportType.BASKETBALL;
    }

    @Override
    public boolean matches(MatchEvent event) {
        return event.type() == EventType.SCORING_RUN && event.intAttr("points", 0) >= minPoints;
    }

    @Override
    public String describe(MatchEvent event) {
        return "RUN in " + event.matchId() + ": " + event.attr("team") + " on a " + event.attr("points") + "-0 run";
    }
}
