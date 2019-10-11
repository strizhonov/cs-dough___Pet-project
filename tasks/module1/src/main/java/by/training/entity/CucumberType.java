package by.training.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum CucumberType {
    BURPLESS,
    LONGSEED,
    SHORTSEED;

    public static Optional<CucumberType> fromString(String type) {
        return Stream.of(CucumberType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
