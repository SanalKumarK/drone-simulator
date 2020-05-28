package org.derivco.entity;

import java.util.concurrent.ThreadLocalRandom;

public enum Traffic {
    HEAVY("HEAVY"), LIGHT("LIGHT"), MODERATE("MODERATE");

    private final String condition;

    Traffic(String condition) {
        this.condition = condition;
    }

    public static Traffic getRandomValue() {
        return values()[ThreadLocalRandom.current().nextInt(1000) % values().length];
    }
}
