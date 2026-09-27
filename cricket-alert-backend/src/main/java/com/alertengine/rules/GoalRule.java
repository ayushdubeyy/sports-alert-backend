package com.alertengine.rules;

import com.alertengine.domain.*;

public class GoalRule implements AlertRule {

    private final String team; // null or blank = any team

    public GoalRule(String team) {
        this.team = team;
    }

    @Override
    public SportType sport() {
        return SportType.FOOTBALL;
    }

    @Override
    public boolean matches(MatchEvent event) {
        if (event.type() != EventType.GOAL) return false;
        return team == null || team.isBlank() || team.equalsIgnoreCase(event.attr("team"));
    }

    @Override
    public String describe(MatchEvent event) {
        return "GOAL in " + event.matchId() + ": " + event.attr("player") + " (" + event.attr("team")
                + ") at " + event.attr("minute") + "'";
    }
}
