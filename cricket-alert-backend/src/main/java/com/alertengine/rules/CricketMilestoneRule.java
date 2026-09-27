package com.alertengine.rules;

import com.alertengine.domain.*;

public class CricketMilestoneRule implements AlertRule {

    private final int minRuns;

    public CricketMilestoneRule(int minRuns) {
        this.minRuns = minRuns;
    }

    @Override
    public SportType sport() {
        return SportType.CRICKET;
    }

    @Override
    public boolean matches(MatchEvent event) {
        return event.type() == EventType.MILESTONE && event.intAttr("runs", 0) >= minRuns;
    }

    @Override
    public String describe(MatchEvent event) {
        return "MILESTONE in " + event.matchId() + ": " + event.attr("player") + " reaches " + event.attr("runs");
    }
}
