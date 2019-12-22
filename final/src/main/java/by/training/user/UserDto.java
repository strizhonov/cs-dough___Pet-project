package by.training.user;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long id;
    private byte[] avatar = new byte[0];
    private String username;
    private String password;
    private String passwordKey;
    private String email;
    private User.UserType type = User.UserType.getDefault();
    private User.Language language = User.Language.getDefault();
    private WalletDto wallet;
    private long playerAccountId;
    private long organizerId;


    public UserDto() {

    }


    public UserDto(long id, byte[] avatar, String username, String password, String passwordKey, String email,
                   User.UserType type, User.Language language, WalletDto wallet, long playerAccountId, long organizerId) {
        this.id = id;
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
        this.wallet = wallet;
        this.playerAccountId = playerAccountId;
        this.organizerId = organizerId;
    }


    private UserDto(Builder builder) {
        setId(builder.id);
        setAvatar(builder.avatar);
        setUsername(builder.username);
        setPassword(builder.password);
        setPasswordKey(builder.passwordKey);
        setEmail(builder.email);
        setType(builder.type);
        setLanguage(builder.language);
        setWallet(builder.wallet);
        setPlayerAccountId(builder.playerAccountId);
        setOrganizerId(builder.organizerId);
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        if (avatar == null) {
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

    public WalletDto getWallet() {
        return wallet;
    }

    public void setWallet(WalletDto wallet) {
        this.wallet = wallet;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                playerAccountId == userDto.playerAccountId &&
                organizerId == userDto.organizerId &&
                Arrays.equals(avatar, userDto.avatar) &&
                Objects.equals(wallet, userDto.wallet) &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(passwordKey, userDto.passwordKey) &&
                Objects.equals(email, userDto.email) &&
                type == userDto.type &&
                language == userDto.language;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, password, passwordKey, email, type, language, wallet, playerAccountId, organizerId);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", avatar=" + Arrays.toString(avatar) +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordKey='" + passwordKey + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", language=" + language +
                ", wallet=" + wallet +
                ", playerAccountId=" + playerAccountId +
                ", organizerId=" + organizerId +
                '}';
    }


    public static final class Builder {
        private long id;
        private byte[] avatar;
        private String username;
        private String password;
        private String passwordKey;
        private String email;
        private User.UserType type;
        private User.Language language;
        private WalletDto wallet;
        private long playerAccountId;
        private long organizerId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder avatar(byte[] val) {
            if (val == null) {
                this.avatar = null;
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

        public Builder type(User.UserType val) {
            type = val;
            return this;
        }

        public Builder language(User.Language val) {
            language = val;
            return this;
        }

        public Builder wallet(WalletDto val) {
            wallet = val;
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
