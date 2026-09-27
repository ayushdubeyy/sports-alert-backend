package com.alertengine.domain;

import java.util.Objects;

public record Subscriber(String id, String name) {

    public Subscriber {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
    }
}
