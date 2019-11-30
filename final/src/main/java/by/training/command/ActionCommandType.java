package by.training.command;

import by.training.security.SecurityType;

import java.util.Optional;
import java.util.stream.Stream;

import static by.training.security.SecurityType.*;

public enum ActionCommandType {
    CHANGE_LANGUAGE_TO_RU(ALL),
    CHANGE_LANGUAGE_TO_EN(ALL),
    CREATE_ORGANIZER(NON_ORGANIZER_USERS),
    CREATE_PLAYER(NON_PLAYER_USERS),
    CREATE_TOURNAMENT(ORGANIZER),
    DEFAULT_COMMAND(ALL),
    JOIN_TOURNAMENT(PLAYER),
    LIST_TEAMS(ALL),
    LIST_TOURNAMENTS(ALL),
    LOGIN(ONLY_ANONYMOUS),
    REGISTER(ONLY_ANONYMOUS),
    SHOW_TEAM(ALL),
    SHOW_PLAYER(ALL),
    TO_GAME_PAGE(ALL),
    TO_ORGANIZER_CREATION(ORGANIZER),
    TO_LOGIN_PAGE(ONLY_ANONYMOUS),
    TO_PLAYER_CREATION(PLAYER),
    TO_TOURNAMENT_CREATION(ORGANIZER),
    TO_TOURNAMENT_PAGE(ALL),
    TO_USER_PAGE(ALL);

    SecurityType securityType;

    ActionCommandType(SecurityType securityType) {
        this.securityType = securityType;

    }

    public SecurityType getSecurityType() {
        return securityType;
    }

    public static Optional<ActionCommandType> from(String name) {
        return Stream.of(ActionCommandType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
    }

}
