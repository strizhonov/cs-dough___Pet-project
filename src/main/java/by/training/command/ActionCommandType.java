package by.training.command;

import by.training.security.AccessAllowedForType;

import java.util.Optional;
import java.util.stream.Stream;

import static by.training.security.AccessAllowedForType.*;

public enum ActionCommandType {

    /**
     * Keep alphabetically
     * <p>
     * Synchronize your updating with ApplicationContext class
     * and ActionCommand implementation.
     */
    CHANGE_LANGUAGE_TO_EN(ANY),
    CHANGE_LANGUAGE_TO_RU(ANY),
    CHANGE_USER_LANGUAGE_TO_EN(USER),
    CHANGE_USER_LANGUAGE_TO_RU(USER),
    CREATE_ORGANIZER(NON_ORGANIZER_USERS),
    CREATE_PLAYER(NON_PLAYER_USERS),
    CREATE_TOURNAMENT(ANY_ORGANIZER),
    DEFAULT_COMMAND(ANY),
    DELETE_ORGANIZER(ANY_ORGANIZER),
    DELETE_PLAYER(ANY_PLAYER),
    DELETE_TOURNAMENT(ANY_ORGANIZER),
    DEPOSIT(USER),
    GET_ORGANIZER_LOGO(ANY),
    GET_PLAYER_PHOTO(ANY),
    GET_TOURNAMENT_LOGO(ANY),
    GET_USER_PHOTO(ANY),
    INCREASE_FIRST_PLAYER_COUNT(TOURNAMENT_GAME_OWNER),
    INCREASE_PLAYER_COUNT(TOURNAMENT_GAME_OWNER),
    JOIN_TOURNAMENT(ANY_PLAYER),
    LEAVE_TOURNAMENT(ANY_PLAYER),
    LOG_OUT(USER),
    LIST_GAMES(ANY),
    LIST_PLAYER_GAMES(ANY),
    LIST_PLAYER_TOURNAMENTS(ANY),
    LIST_PLAYERS(ANY),
    LIST_TOURNAMENTS(ANY),
    LIST_USERS(ADMIN),
    LOGIN(ANONYMOUS),
    SIGN_UP(ANONYMOUS),
    SHOW_PARTICIPANTS(ANY),
    SHOW_PLAYER(ANY),
    SHOW_ORGANIZER(ANY),
    TO_BRACKET_PAGE(ANY),
    TO_GAME_PAGE(ANY),
    TO_HOME_PAGE(ANY),
    TO_ORGANIZER_CREATION(NON_ORGANIZER_USERS),
    TO_ORGANIZER_EDITING(ANY_ORGANIZER),
    TO_ORGANIZER_TOURNAMENTS(ANY),
    TO_PLAYER_CREATION(NON_PLAYER_USERS),
    TO_PLAYER_EDITING(ANY_PLAYER),
    TO_TOURNAMENT_CREATION(ANY_ORGANIZER),
    TO_TOURNAMENT_PAGE(ANY),
    TO_USER_PAGE(ADMIN),
    UPDATE_EMAIL(USER),
    UPDATE_ORGANIZER(ANY_ORGANIZER),
    UPDATE_PLAYER(ANY_PLAYER),
    UPDATE_USERNAME(USER),
    UPLOAD_USER_PHOTO(USER),
    WITHDRAW(USER);

    AccessAllowedForType accessType;


    ActionCommandType(AccessAllowedForType accessType) {
        this.accessType = accessType;
    }


    public static Optional<ActionCommandType> from(String name) {
        return Stream.of(ActionCommandType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
    }


    public AccessAllowedForType getAccessType() {
        return accessType;
    }

}
