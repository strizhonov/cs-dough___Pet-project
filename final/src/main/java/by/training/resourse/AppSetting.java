package by.training.resourse;

import by.training.core.Bean;
import by.training.util.ResourceUtil;

import java.util.ResourceBundle;

@Bean
public class AppSetting {

    private static final String SETTINGS_PROPERTIES_NAME = AttributesContainer.SETTINGS.toString();
    private final ResourceBundle SETTINGS = ResourceBundle.getBundle(SETTINGS_PROPERTIES_NAME);


    public String get(SettingName key) {
        return SETTINGS.getString(key.toString());
    }

    public enum SettingName {

        /**
         * Keep alphabetically.
         * <p>
         * Synchronize your updates with setting.properties
         */

        APP_WALLET_HOLDER_ID,
        APP_WALLET_ID,
        CONNECTION_LIFETIME_MS,
        CONNECTION_POOL_SIZE,
        DB_DRIVER,
        DB_PASSWORD,
        DB_URL,
        DB_USER,
        DEFAULT_CURRENCY,
        DEFAULT_DATE_FORMAT,
        DEFAULT_LANGUAGE,
        DEFAULT_POINTS_TO_WIN,
        DEFAULT_TOURNAMENT_STATUS,
        DEFAULT_USER_TYPE,

        /**
         * For future impls.
         */
        EIGHT_PLAYERS_EIGHTH_PRIZE_RATE,
        EIGHT_PLAYERS_FIFTH_PRIZE_RATE,
        EIGHT_PLAYERS_FIRST_PRIZE_RATE,
        EIGHT_PLAYERS_FOURTH_PRIZE_RATE,
        EIGHT_PLAYERS_SECOND_PRIZE_RATE,
        EIGHT_PLAYERS_SEVENTH_PRIZE_RATE,
        EIGHT_PLAYERS_SIXTH_PRIZE_RATE,
        EIGHT_PLAYERS_THIRD_PRIZE_RATE,

        FOUR_PLAYERS_FIRST_PRIZE_RATE,
        FOUR_PLAYERS_FOURTH_PRIZE_RATE,
        FOUR_PLAYERS_SECOND_PRIZE_RATE,
        FOUR_PLAYERS_THIRD_PRIZE_RATE,
        GAMES_INTERVAL,
        HOME_PAGE_MAX_GAME_RESULTS,
        IMAGE_ALLOWED_SIZE,
        IMAGE_FORMAT,
        STANDARD_CHARSET_NAME,
        TERMINATOR_EXECUTOR_DELAY,
        TERMINATOR_EXECUTOR_PERIOD,
        TERMINATOR_EXECUTOR_TIME_UNIT,
        XSS_PROTECTION_NAME,
        XSS_PROTECTION_TYPE,
        TWO_PLAYERS_FIRST_PRIZE_RATE,
        TWO_PLAYERS_SECOND_PRIZE_RATE;

        public String toString() {
            return ResourceUtil.fromConstantToProperty(this.name());
        }

    }

}
