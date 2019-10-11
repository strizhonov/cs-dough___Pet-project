package by.training.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum VegetableType {
    CARROT,
    CUCUMBER,
    LEEK,
    TOMATO;

    public static Optional<VegetableType> fromString(String type) {
        return Stream.of(VegetableType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
