package by.training.user;

import java.io.Serializable;
import java.util.Objects;

public class LoginDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private String username;
    private String password;


    public LoginDto() {
    }


    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDto loginDto = (LoginDto) o;
        return Objects.equals(username, loginDto.username) &&
                Objects.equals(password, loginDto.password);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }


    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}

