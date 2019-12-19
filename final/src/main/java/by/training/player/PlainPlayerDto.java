package by.training.player;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class PlainPlayerDto implements Serializable {

    private static final long serialVersionUID = 4L;

    protected long id;
    protected String name;
    protected String nickname;
    protected String surname;
    protected byte[] photo = new byte[]{};
    protected double totalWon;
    protected long userId;


    public PlainPlayerDto() {
    }


    public PlainPlayerDto(long id, String name, String nickname, String surname, byte[] photo,
                          double totalWon, long userId) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.surname = surname;
        if (photo == null) {
            this.photo = null;
        } else {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
        this.totalWon = totalWon;
        this.userId = userId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getPhoto() {
        if (photo == null) {
            return null;
        } else {
            return Arrays.copyOf(photo, photo.length);
        }
    }

    public void setPhoto(byte[] photo) {
        if (photo == null) {
            this.photo = null;
        } else {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
    }

    public double getTotalWon() {
        return totalWon;
    }

    public void setTotalWon(double totalWon) {
        this.totalWon = totalWon;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void increaseTotalWon(double value) {
        totalWon += value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainPlayerDto that = (PlainPlayerDto) o;
        return id == that.id &&
                Double.compare(that.totalWon, totalWon) == 0 &&
                userId == that.userId &&
                Objects.equals(name, that.name) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(surname, that.surname) &&
                Arrays.equals(photo, that.photo);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, nickname, surname, totalWon, userId);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }


    @Override
    public String toString() {
        return "PlainPlayerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", surname='" + surname + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", totalWon=" + totalWon +
                ", userId=" + userId +
                '}';
    }


}
