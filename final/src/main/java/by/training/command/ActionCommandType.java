package by.training.command;

import by.training.security.AccessAllowedForType;

import java.util.Optional;
import java.util.stream.Stream;

import static by.training.security.AccessAllowedForType.*;

public enum ActionCommandType {
    CHANGE_LANGUAGE_TO_EN(ANY),
    CHANGE_LANGUAGE_TO_RU(ANY),
    CHANGE_USER_LANGUAGE_TO_EN(USER),
    CHANGE_USER_LANGUAGE_TO_RU(USER),
    CREATE_ORGANIZER(NON_ORGANIZER_USERS),
    CREATE_PLAYER(NON_PLAYER_USERS),
    CREATE_TOURNAMENT(ORGANIZER_OWNER),
    DEFAULT_COMMAND(ANY),
    GET_ORGANIZER_LOGO(ANY),
    GET_PLAYER_PHOTO(USER),
    GET_TOURNAMENT_LOGO(ANY),
    GET_USER_PHOTO(ANY),
    JOIN_TOURNAMENT(PLAYER),
    LOG_OUT(USER),
    LIST_GAMES(ANY),
    LIST_PLAYERS(ANY),
    LIST_TEAMS(ANY),
    LIST_TOURNAMENTS(ANY),
    LOGIN(ANONYMOUS),
    REGISTER(ANONYMOUS),
    SHOW_PLAYER(ANY),
    SHOW_ORGANIZER(ANY),
    TO_GAME_PAGE(ANY),
    TO_HOME_PAGE(ANY),
    TO_ORGANIZER_CREATION(NON_ORGANIZER_USERS),
    TO_LOGIN_PAGE(ANONYMOUS),
    TO_PLAYER_CREATION(NON_PLAYER_USERS),
    TO_TOURNAMENT_CREATION(ORGANIZER),
    TO_TOURNAMENT_PAGE(ANY),
    TO_ONE_GAME_BRACKET(ANY),
    TO_USER_PAGE(USER),
    UPDATE_EMAIL(USER),
    UPDATE_USERNAME(USER),
    UPLOAD_TEMP_ORGANIZER_LOGO(NON_ORGANIZER_USERS),
    UPLOAD_USER_PHOTO(USER);

    AccessAllowedForType accessType;

    ActionCommandType(AccessAllowedForType accessType) {
        this.accessType = accessType;

    }

    public static Optional<ActionCommandType> from(String name) {
        return Stream.of(ActionCommandType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
    }

    public AccessAllowedForType getSecurityType() {
        return accessType;
    }

}
