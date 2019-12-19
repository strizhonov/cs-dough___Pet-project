package by.training.player;

import by.training.core.Entity;

import java.util.Arrays;
import java.util.Objects;

public class Player extends Entity {

    private static final long serialVersionUID = 4L;

    private byte[] photo = new byte[0];
    private String nickname;
    private String name;
    private String surname;
    private double totalWon;
    private long userId;

    public Player() {
    }


    public Player(byte[] photo, String nickname, String name, String surname, double totalWon, long userId) {
        if (photo == null) {
            this.photo = null;
        } else {
            this.photo = Arrays.copyOf(photo, photo.length);
        }
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.totalWon = totalWon;
        this.userId = userId;
    }


    private Player(Builder builder) {
        setId(builder.id);
        setPhoto(builder.photo);
        setNickname(builder.nickname);
        setName(builder.name);
        setSurname(builder.surname);
        setTotalWon(builder.totalWon);
        setUserId(builder.userId);
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Double.compare(player.totalWon, totalWon) == 0 &&
                userId == player.userId &&
                Arrays.equals(photo, player.photo) &&
                Objects.equals(nickname, player.nickname) &&
                Objects.equals(name, player.name) &&
                Objects.equals(surname, player.surname);
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(nickname, name, surname, totalWon, userId);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }


    @Override
    public String toString() {
        return "Player{" +
                "photo=" + Arrays.toString(photo) +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", totalWon=" + totalWon +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }


    public static final class Builder {

        private long id;
        private byte[] photo = new byte[0];
        private String nickname;
        private String name;
        private String surname;
        private double totalWon;
        private long userId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder photo(byte[] val) {
            if (val == null) {
                this.photo = null;
            } else {
                this.photo = Arrays.copyOf(photo, photo.length);
            }
            return this;
        }

        public Builder nickname(String val) {
            nickname = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder surname(String val) {
            surname = val;
            return this;
        }

        public Builder totalWon(double val) {
            totalWon = val;
            return this;
        }

        public Builder userId(long val) {
            userId = val;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }

}
