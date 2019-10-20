package by.training.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum DeviceType {
    MOTHERBOARD, PROCESSOR, MOUSE;

    public static Optional<DeviceType> fromString(String type) {
        return Stream.of(DeviceType.values())
                .filter((t) -> t.name().equalsIgnoreCase(type))
                .findFirst();
    }
}
