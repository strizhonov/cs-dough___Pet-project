package by.training.constant;

import by.training.entity.Game;
import by.training.entity.Game.GameType;
import by.training.entity.Tournament;
import by.training.entity.Tournament.TournamentStatus;
import by.training.entity.User;
import by.training.entity.User.UserType;
import by.training.entity.User.Language;
import by.training.entity.Wallet;
import by.training.entity.Wallet.*;

public class ValuesContainer {

    /**
     * Keep alphabetically
     */

    public static final CurrencyType DEFAULT_CURRENCY = Wallet.CurrencyType.USD;
    public static final GameType DEFAULT_GAME_TYPE = Game.GameType.BO3;
    public static final Language DEFAULT_LANGUAGE = User.Language.EN;
    public static final TournamentStatus DEFAULT_TOURNAMENT_STATUS = Tournament.TournamentStatus.UPCOMING;
    public static final UserType DEFAULT_USER_TYPE = User.UserType.ANONYMOUS;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    public static final String EN = "en";
    public static final int HOME_PAGE_MAX_GAME_RESULTS = 5;
    public static final String PASSWORD_REGEX = "^[\\w!@#$%^&]{6,}$";
    public static final String REFERER = "Referer";
    public static final String RU = "ru";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String STANDARD_CHARSET_NAME = "UTF-8";
    public static final String URI_MAIN_PATTERN = "/app";
    public static final String USERNAME_REGEX = "^[A-Za-z0-9]+$";
    public static final String XSS_PROTECTION_NAME = "X-XSS-Protection";
    public static final String XSS_PROTECTION_TYPE = "1;mode=block";

}
