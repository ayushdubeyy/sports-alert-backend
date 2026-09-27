package com.alertengine.rules;

import com.alertengine.domain.*;

public class WicketRule implements AlertRule {

    @Override
    public SportType sport() {
        return SportType.CRICKET;
    }

    @Override
    public boolean matches(MatchEvent event) {
        return event.type() == EventType.WICKET;
    }

    @Override
    public String describe(MatchEvent event) {
        return "WICKET in " + event.matchId() + ": " + event.attr("player") + " out for " + event.attr("runs");
    }
}
