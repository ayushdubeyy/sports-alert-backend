package com.alertengine.rules;

import com.alertengine.domain.AlertRule;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Factory backed by a registry: adding a rule type = one register() call, no if/else or switch to edit.
 */
public class RuleFactory {

    private final Map<String, Function<Map<String, String>, AlertRule>> creators = new ConcurrentHashMap<>();

    public RuleFactory() {
        register("CRICKET_WICKET", p -> new WicketRule());
        register("CRICKET_MILESTONE", p -> new CricketMilestoneRule(intParam(p, "minRuns", 50)));
        register("FOOTBALL_GOAL", p -> new GoalRule(p.get("team")));
        register("TENNIS_MATCH_POINT", p -> new MatchPointRule());
        register("BASKETBALL_SCORING_RUN", p -> new ScoringRunRule(intParam(p, "minPoints", 10)));
    }

    public void register(String key, Function<Map<String, String>, AlertRule> creator) {
        creators.put(key, creator);
    }

    public AlertRule create(String key, Map<String, String> params) {
        Function<Map<String, String>, AlertRule> creator = creators.get(key);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown rule type: " + key);
        }
        return creator.apply(params == null ? Map.of() : params);
    }

    private static int intParam(Map<String, String> params, String key, int fallback) {
        String curr = params.get(key);
        return curr == null ? fallback : Integer.parseInt(curr);
    }
}
