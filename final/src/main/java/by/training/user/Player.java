package by.training.user;

import by.training.common.Entity;

public class Player extends Entity {

    private String name;
    private String surname;
    private String nickname;
    private byte[] photo;
    private String country;
    private double totalWon;
    private long userId;

    public Player() {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public static final class Builder {
        private long id;
        private String name;
        private String surname;
        private String nickname;
        private byte[] photo;
        private String country;
        private double totalWon;
        private long userId;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder photo(byte[] photo) {
            this.photo = photo;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder totalWon(double totalWon) {
            this.totalWon = totalWon;
            return this;
        }

        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public Player build() {
            Player player = new Player();
            player.setId(id);
            player.setName(name);
            player.setSurname(surname);
            player.setNickname(nickname);
            player.setPhoto(photo);
            player.setCountry(country);
            player.setTotalWon(totalWon);
            player.setUserId(userId);
            return player;
        }
    }
}
