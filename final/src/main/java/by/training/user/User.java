package by.training.user;

import by.training.core.AppSetting;
import by.training.common.Entity;
import by.training.core.ApplicationContext;

import java.util.Optional;
import java.util.stream.Stream;

public class User extends Entity {

    private byte[] avatar = new byte[0];
    private String username;
    private String password;
    private String passwordKey;
    private String email;
    private UserType type = UserType.getDefault();
    private Language language = Language.getDefault();


    public User() {
    }

    private User(Builder builder) {
        setId(builder.id);
        setAvatar(builder.avatar);
        setUsername(builder.username);
        setPassword(builder.password);
        setPasswordKey(builder.passwordKey);
        setEmail(builder.email);
        setType(builder.type);
        setLanguage(builder.language);
    }

    public enum UserType {
        ANONYMOUS, USER, ADMIN;

        private static final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);

        public static UserType getDefault() {
            return fromString(setting.get(AppSetting.SettingName.DEFAULT_USER_TYPE)).orElse(null);
        }

        public static Optional<UserType> fromString(String type) {
            return Stream.of(UserType.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }
    }

    public enum Language {
        EN, RU;

        public static Language getDefault() {
            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
            return fromLocaleString(setting.get(AppSetting.SettingName.DEFAULT_LANGUAGE)).orElse(null);
        }

        public static Optional<Language> fromLocaleString(String type) {
            return Stream.of(Language.values())
                    .filter(t -> type != null && type.toLowerCase().contains(t.name().toLowerCase()))
                    .findFirst();
        }

        public Language getSwitched() {
            return this == EN ? RU : EN;
        }

    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public static final class Builder {
        private long id;
        private byte[] avatar = new byte[0];
        private String username;
        private String password;
        private String passwordKey;
        private String email;
        private UserType type = UserType.getDefault();
        private Language language = Language.getDefault();

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder avatar(byte[] val) {
            avatar = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder passwordKey(String val) {
            passwordKey = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder type(UserType val) {
            type = val;
            return this;
        }

        public Builder language(Language val) {
            language = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
