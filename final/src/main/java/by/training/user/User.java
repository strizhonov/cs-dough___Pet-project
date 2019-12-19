package by.training.user;

import by.training.core.ApplicationContext;
import by.training.core.Entity;
import by.training.resourse.AppSetting;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class User extends Entity {

    private static final long serialVersionUID = 4L;

    private byte[] avatar = new byte[0];
    private String username;
    private String password;
    private String passwordKey;
    private String email;
    private UserType type = UserType.getDefault();
    private Language language = Language.getDefault();


    public User() {
    }


    public User(byte[] avatar, String username, String password, String passwordKey, String email, UserType type,
                Language language) {
        if (avatar == null) {
            this.avatar = null;
        } else {
            this.avatar = Arrays.copyOf(avatar, avatar.length);
        }
        this.username = username;
        this.password = password;
        this.passwordKey = passwordKey;
        this.email = email;
        this.type = type;
        this.language = language;
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

        USER, ADMIN;


        public static UserType getDefault() {
            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);

            if (setting == null) {
                return null;
            }

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

            if (setting == null) {
                return null;
            }

            return fromLocaleString(setting.get(AppSetting.SettingName.DEFAULT_LANGUAGE)).orElse(null);

        }


        public static Optional<Language> fromLocaleString(String type) {
            return Stream.of(Language.values())
                    .filter(t -> type != null && type.toLowerCase().contains(t.name().toLowerCase()))
                    .findFirst();
        }

    }


    public byte[] getAvatar() {
        if (this.avatar == null) {
            return null;
        } else {
            return Arrays.copyOf(avatar, avatar.length);
        }
    }

    public void setAvatar(byte[] avatar) {
        if (avatar == null) {
            this.avatar = null;
        } else {
            this.avatar = Arrays.copyOf(avatar, avatar.length);
        }
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Arrays.equals(avatar, user.avatar) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(passwordKey, user.passwordKey) &&
                Objects.equals(email, user.email) &&
                type == user.type &&
                language == user.language;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(username, password, passwordKey, email, type, language);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }


    @Override
    public String toString() {
        return "User{" +
                "avatar=" + Arrays.toString(avatar) +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", language=" + language +
                ", id=" + id +
                '}';
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
            if (val == null) {
                avatar = null;
            } else {
                avatar = Arrays.copyOf(val, val.length);
            }
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
