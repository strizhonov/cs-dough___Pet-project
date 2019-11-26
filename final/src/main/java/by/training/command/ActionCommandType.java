package by.training.command;

import java.util.Optional;
import java.util.stream.Stream;

public enum ActionCommandType {
    CREATE_ORGANIZER,
    CREATE_PLAYER,
    CREATE_TOURNAMENT,
    DEFAULT_COMMAND,
    JOIN_TOURNAMENT,
    LIST_TEAMS,
    LIST_TOURNAMENTS,
    LOGIN,
    REGISTER,
    SHOW_TEAM,
    SHOW_PLAYER,
    TO_GAME_PAGE,
    TO_ORGANIZER_CREATION,
    TO_LOGIN_PAGE,
    TO_PLAYER_CREATION,
    TO_TOURNAMENT_CREATION,
    TO_TOURNAMENT_PAGE;

    public static Optional<ActionCommandType> from(String name) {
        return Stream.of(ActionCommandType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
    }

}
