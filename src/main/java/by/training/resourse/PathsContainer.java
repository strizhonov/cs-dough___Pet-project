package by.training.resourse;

public class PathsContainer {

    /**
     * Keep alphabetically
     * <p>
     * <p>
     * <p>
     * Commands
     */

    public static final String COMMAND_CHANGE_SESSION_LANGUAGE = "/?command=change_language_to_";
    public static final String COMMAND_CHANGE_LANGUAGE = "/?command=change_user_language_to_";
    public static final String COMMAND_CREATE_ORGANIZER = "/?command=create_organizer";
    public static final String COMMAND_CREATE_PLAYER = "/?command=create_player";
    public static final String COMMAND_CREATE_TOURNAMENT = "/?command=create_tournament";
    public static final String COMMAND_DELETE_ORGANIZER = "/?command=delete_organizer&id=";
    public static final String COMMAND_DELETE_PLAYER = "/?command=delete_player&id=";
    public static final String COMMAND_DELETE_TOURNAMENT = "/?command=delete_tournament&id=";
    public static final String COMMAND_DEPOSIT = "/?command=deposit";
    public static final String COMMAND_GET_PLAYER_PHOTO = "/?command=get_player_photo&id=";
    public static final String COMMAND_GET_ORGANIZER_LOGO = "/?command=get_organizer_logo&id=";
    public static final String COMMAND_GET_TOURNAMENT_LOGO = "/?command=get_tournament_logo&id=";
    public static final String COMMAND_GET_USER_PHOTO = "/?command=get_user_photo&id=";
    public static final String COMMAND_INCREASE_PLAYER_COUNT = "/?command=increase_player_count&id=";
    public static final String COMMAND_JOIN_TOURNAMENT = "/?command=join_tournament&id=";
    public static final String COMMAND_LEAVE_TOURNAMENT = "/?command=leave_tournament&id=";
    public static final String COMMAND_LIST_GAMES = "/?command=list_games";
    public static final String COMMAND_LIST_PLAYER_GAMES = "/?command=list_player_games&id=";
    public static final String COMMAND_LIST_PLAYER_TOURNAMENTS = "/?command=list_player_tournaments&id=";
    public static final String COMMAND_LIST_PLAYERS = "/?command=list_players";
    public static final String COMMAND_LIST_TOURNAMENTS = "/?command=list_tournaments";
    public static final String COMMAND_LIST_USERS = "/?command=list_users";
    public static final String COMMAND_LOGIN = "/?command=login";
    public static final String COMMAND_LOG_OUT = "/?command=log_out";
    public static final String COMMAND_SHOW_ORGANIZER = "/?command=show_organizer&id=";
    public static final String COMMAND_SHOW_PARTICIPANTS = "/?command=show_participants&id=";
    public static final String COMMAND_SHOW_PLAYER = "/?command=show_player&id=";
    public static final String COMMAND_SIGN_UP = "/?command=sign_up";
    public static final String COMMAND_TO_BRACKET_PAGE = "/?command=to_bracket_page&id=";
    public static final String COMMAND_TO_GAME_PAGE = "/?command=to_game_page&id=";
    public static final String COMMAND_TO_HOME_PAGE = "/?command=to_home_page";
    public static final String COMMAND_TO_PLAYER_CREATION = "/?command=to_player_creation";
    public static final String COMMAND_TO_PLAYER_EDITING = "/?command=to_player_editing";
    public static final String COMMAND_TO_ORGANIZER_CREATION = "/?command=to_organizer_creation";
    public static final String COMMAND_TO_ORGANIZER_EDITING = "/?command=to_organizer_editing";
    public static final String COMMAND_TO_ORGANIZER_TOURNAMENTS = "/?command=to_organizer_tournaments&id=";
    public static final String COMMAND_TO_TOURNAMENT_CREATION = "/?command=to_tournament_creation";
    public static final String COMMAND_TO_TOURNAMENT_PAGE = "/?command=to_tournament_page&id=";
    public static final String COMMAND_TO_USER_PAGE = "/?command=to_user_page&id=";
    public static final String COMMAND_UPDATE_EMAIL = "/?command=update_email";
    public static final String COMMAND_UPDATE_ORGANIZER = "/?command=update_organizer&id=";
    public static final String COMMAND_UPDATE_PLAYER = "/?command=update_player";
    public static final String COMMAND_UPDATE_USERNAME = "/?command=update_username";
    public static final String COMMAND_UPLOAD_USER_PHOTO = "/?command=upload_user_photo";
    public static final String COMMAND_WITHDRAW = "/?command=withdraw";


    /**
     * Files
     */
    public static final String FILE_ACCESS_DENIED = "/jsp/error/access-denied.jsp";
    public static final String FILE_BLANK_LOGO = "/img/blank-logo.jpg";
    public static final String FILE_DEF_TOURNAMENT_LOGO = "/img/def-tournament.png";
    public static final String FILE_ERROR_PAGE = "/jsp/error/general-error.jsp";

    // Created in order to implement "F5" protection (due to unavailability of forward request with message)
    public static final String FILE_AFTER_SIGN_UP = "/jsp/after-sign-up.jsp";
    public static final String FILE_GAME_PAGE = "/jsp/game-page.jsp";
    public static final String FILE_GAMES = "/jsp/games.jsp";
    public static final String FILE_HOME = "/jsp/home.jsp";
    public static final String FILE_INDEX = "/index.jsp";
    public static final String FILE_LOGIN = "/jsp/login.jsp";
    public static final String FILE_NOBODY = "/img/nobody.jpg";
    public static final String FILE_ONE_GAME_BRACKET_PAGE = "/jsp/one-game-bracket-page.jsp";
    public static final String FILE_ORGANIZER_CREATION = "/jsp/organizer-creation.jsp";
    public static final String FILE_ORGANIZER_EDITING = "/jsp/organizer-editing.jsp";
    public static final String FILE_ORGANIZER_PROFILE_PAGE = "/jsp/organizer-profile.jsp";
    public static final String FILE_ORGANIZER_TOURNAMENTS_PAGE = "/jsp/organizer-tournaments.jsp";
    public static final String FILE_PLACEMENTS = "/jsp/placements.jsp";
    public static final String FILE_PLAYER_CREATION = "/jsp/player-creation.jsp";
    public static final String FILE_PLAYER_EDITING = "/jsp/player-editing.jsp";
    public static final String FILE_PLAYER_GAMES = "/jsp/player-games.jsp";
    public static final String FILE_PLAYER_PROFILE_PAGE = "/jsp/player-profile.jsp";
    public static final String FILE_PLAYER_TOURNAMENTS = "/jsp/player-tournaments.jsp";
    public static final String FILE_PLAYERS = "/jsp/players.jsp";
    public static final String FILE_SEVEN_GAME_BRACKET_PAGE = "/jsp/seven-game-bracket-page.jsp";
    public static final String FILE_THREE_GAME_BRACKET_PAGE = "/jsp/three-game-bracket-page.jsp";
    public static final String FILE_TOURNAMENT_CREATION_PAGE = "/jsp/tournament-creation.jsp";
    public static final String FILE_TOURNAMENT_PAGE = "/jsp/tournament-page.jsp";
    public static final String FILE_TOURNAMENTS_PAGE = "/jsp/tournaments.jsp";
    public static final String FILE_USER_PROFILE_PAGE = "/jsp/user-profile.jsp";
    public static final String FILE_WALLET_PAGE = "/jsp/wallet.jsp";


    /**
     * Paths
     */
    public static final String PATH_LINKED_IN = "https://www.linkedin.com/in/uladzislau-stryzhonak-586922199";
    public static final String PATH_GITHUB = "https://github.com/strizhonov";
    public static final String PATH_BITBUCKET = "https://bitbucket.org/strizhonov/java-web-training/src/master/final";
    public static final String PATH_FAKE_GAME_TRANSLATION = "https://player.twitch.tv/?channel=esl_csgo&muted=true&autoplay=false";

}
