package by.training.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class RegistrationDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private byte[] defAvatar;
    private String username;
    private String password;
    private String email;
    private User.Language language;
    private Wallet.Currency currency = Wallet.Currency.getDefault();


    public RegistrationDto() {
    }


    public RegistrationDto(byte[] defAvatar, String username, String password, String email, User.Language language,
                           Wallet.Currency currency) {
        this.defAvatar = defAvatar;
        this.username = username;
        this.password = password;
        this.email = email;
        this.language = language;
        this.currency = currency;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public byte[] getDefAvatar() {
        return defAvatar;
    }

    public void setDefAvatar(byte[] defAvatar) {
        this.defAvatar = defAvatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Language getLanguage() {
        return language;
    }

    public void setLanguage(User.Language language) {
        this.language = language;
    }

    public Wallet.Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Wallet.Currency currency) {
        this.currency = currency;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationDto that = (RegistrationDto) o;
        return Arrays.equals(defAvatar, that.defAvatar) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                language == that.language &&
                currency == that.currency;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(username, password, email, language, currency);
        result = 31 * result + Arrays.hashCode(defAvatar);
        return result;
    }


    @Override
    public String toString() {
        return "RegistrationDto{" +
                "defAvatar=" + Arrays.toString(defAvatar) +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", language=" + language +
                ", currency=" + currency +
                '}';
    }
}
