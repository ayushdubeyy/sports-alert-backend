package com.alertengine.rules;

import com.alertengine.domain.*;

public class MatchPointRule implements AlertRule {

    @Override
    public SportType sport() {
        return SportType.TENNIS;
    }

    @Override
    public boolean matches(MatchEvent event) {
        return event.type() == EventType.MATCH_POINT;
    }

    @Override
    public String describe(MatchEvent event) {
        return "MATCH POINT in " + event.matchId() + ": " + event.attr("player") + " leads " + event.attr("score");
    }
}
