package by.training.core;

import by.training.constant.AttributesContainer;

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
         * Keep alphabetically
         */

        CONNECTION_LIFETIME_MS,
        CONNECTION_POOL_SIZE,
        DB_DRIVER,
        DB_PASSWORD,
        DB_URL,
        DB_USER,
        DEFAULT_CURRENCY,
        DEFAULT_DATE_FORMAT,
        DEFAULT_LANGUAGE,
        DEFAULT_TOURNAMENT_STATUS,
        DEFAULT_USER_TYPE,
        EMAIL_REGEXP,
        HOME_PAGE_MAX_GAME_RESULTS,
        IMAGE_FORMAT,
        PASSWORD_REGEXP,
        TERMINATOR_EXECUTOR_DELAY,
        TERMINATOR_EXECUTOR_PERIOD,
        TERMINATOR_EXECUTOR_TIME_UNIT,
        STANDARD_CHARSET_NAME,
        USERNAME_REGEXP,
        XSS_PROTECTION_NAME,
        XSS_PROTECTION_TYPE;

        public String toString() {
            return this.name().toLowerCase().replace('_', '.');
        }

    }

}
