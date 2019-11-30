package by.training.entity;

import by.training.constant.ValuesContainer;

import java.util.Optional;
import java.util.stream.Stream;

public class User extends Entity {

    private byte[] avatar = new byte[0];
    private String username;
    private String password;
    private String passwordKey;
    private String email;
    private UserType type = ValuesContainer.DEFAULT_USER_TYPE;
    private Language language = ValuesContainer.DEFAULT_LANGUAGE;
    private long walletId;

    public enum UserType {
        ANONYMOUS, USER, ADMIN;

        public static Optional<UserType> fromString(String type) {
            return Stream.of(UserType.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }
    }

    public enum Language {
        EN, RU;

        public static Optional<Language> fromLocaleString(String type) {
            return Stream.of(Language.values())
                    .filter(t -> type != null && type.toLowerCase().contains(t.name().toLowerCase()))
                    .findFirst();
        }

        public Language getSwitched() {
            if (this == EN) {
                return RU;
            } else {
                return EN;
            }
        }

    }

    public User() {
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

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }


    public static final class Builder {
        protected long id;
        private byte[] avatar = new byte[0];
        private String username;
        private String password;
        private String passwordKey;
        private String email;
        private UserType type = ValuesContainer.DEFAULT_USER_TYPE;
        private Language language = ValuesContainer.DEFAULT_LANGUAGE;
        private long walletId;

        private Builder() {
        }

        public static Builder anUser() {
            return new Builder();
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder avatar(byte[] avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder passwordKey(String passwordKey) {
            this.passwordKey = passwordKey;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder type(UserType type) {
            this.type = type;
            return this;
        }

        public Builder language(Language language) {
            this.language = language;
            return this;
        }

        public Builder walletId(long walletId) {
            this.walletId = walletId;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setAvatar(avatar);
            user.setUsername(username);
            user.setPassword(password);
            user.setPasswordKey(passwordKey);
            user.setEmail(email);
            user.setType(type);
            user.setLanguage(language);
            user.setWalletId(walletId);
            return user;
        }
    }
}
