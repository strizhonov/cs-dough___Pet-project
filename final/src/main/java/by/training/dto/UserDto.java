package by.training.dto;

import by.training.constant.ValuesContainer;
import by.training.entity.User;

public class UserDto {

    private long id;
    private byte[] avatar = new byte[]{};
    private String username;
    private String password;
    private String passwordKey;
    private String email;
    private User.UserType type = ValuesContainer.DEFAULT_USER_TYPE;
    private User.Language language = ValuesContainer.DEFAULT_LANGUAGE;
    private long walletId;
    private long playerAccountId;
    private long organizerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User.UserType getType() {
        return type;
    }

    public void setType(User.UserType type) {
        this.type = type;
    }

    public User.Language getLanguage() {
        return language;
    }

    public void setLanguage(User.Language language) {
        this.language = language;
    }

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
    }

    public long getPlayerAccountId() {
        return playerAccountId;
    }

    public void setPlayerAccountId(long playerAccountId) {
        this.playerAccountId = playerAccountId;
    }

    public long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(long organizerId) {
        this.organizerId = organizerId;
    }

    public static final class Builder {
        private long id;
        private byte[] avatar = new byte[]{};
        private String username;
        private String password;
        private String passwordKey;
        private String email;
        private User.UserType type = ValuesContainer.DEFAULT_USER_TYPE;
        private User.Language language = ValuesContainer.DEFAULT_LANGUAGE;
        private long walletId;
        private long playerAccountId;
        private long organizerId;

        private Builder() {
        }

        public static Builder anUserDto() {
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

        public Builder type(User.UserType type) {
            this.type = type;
            return this;
        }

        public Builder language(User.Language language) {
            this.language = language;
            return this;
        }

        public Builder walletId(long walletId) {
            this.walletId = walletId;
            return this;
        }

        public Builder playerAccountId(long playerAccountId) {
            this.playerAccountId = playerAccountId;
            return this;
        }

        public Builder organizerId(long organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public UserDto build() {
            UserDto userDto = new UserDto();
            userDto.setId(id);
            userDto.setAvatar(avatar);
            userDto.setUsername(username);
            userDto.setPassword(password);
            userDto.setPasswordKey(passwordKey);
            userDto.setEmail(email);
            userDto.setType(type);
            userDto.setLanguage(language);
            userDto.setWalletId(walletId);
            userDto.setPlayerAccountId(playerAccountId);
            userDto.setOrganizerId(organizerId);
            return userDto;
        }
    }
}
