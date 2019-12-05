package by.training.dto;

import by.training.constant.ValuesContainer;
import by.training.entity.User;
import by.training.entity.Wallet;

public class UserDto {

    private long id;
    private byte[] avatar = new byte[]{};
    private String username;
    private String password;
    private String passwordKey;
    private String email;
    private User.UserType type = ValuesContainer.DEFAULT_USER_TYPE;
    private User.Language language = ValuesContainer.DEFAULT_LANGUAGE;
    private double balance = 0;
    private Wallet.Currency currency = ValuesContainer.DEFAULT_CURRENCY;
    private long playerAccountId;
    private long organizerId;

    private UserDto(Builder builder) {
        setId(builder.id);
        setAvatar(builder.avatar);
        setUsername(builder.username);
        setPassword(builder.password);
        setPasswordKey(builder.passwordKey);
        setEmail(builder.email);
        setType(builder.type);
        setLanguage(builder.language);
        setBalance(builder.balance);
        setCurrency(builder.currency);
        setPlayerAccountId(builder.playerAccountId);
        setOrganizerId(builder.organizerId);
    }

    public UserDto() {

    }

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Wallet.Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Wallet.Currency currency) {
        this.currency = currency;
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
        private double balance;
        private Wallet.Currency currency = ValuesContainer.DEFAULT_CURRENCY;
        private long playerAccountId;
        private long organizerId;

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

        public Builder type(User.UserType val) {
            type = val;
            return this;
        }

        public Builder language(User.Language val) {
            language = val;
            return this;
        }

        public Builder balance(double val) {
            balance = val;
            return this;
        }

        public Builder currency(Wallet.Currency val) {
            currency = val;
            return this;
        }

        public Builder playerAccountId(long val) {
            playerAccountId = val;
            return this;
        }

        public Builder organizerId(long val) {
            organizerId = val;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
