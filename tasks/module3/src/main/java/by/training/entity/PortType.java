package by.training.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum PortType {
    COM, USB, LPT;

    public static Optional<PortType> fromString(String type) {
        return Stream.of(PortType.values())
                .filter((t) -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
