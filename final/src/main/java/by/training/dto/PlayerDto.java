package by.training.dto;

import java.util.List;

public class PlayerDto {

    private long id;
    private String name;
    private String nickname;
    private String surname;
    private byte[] photo = new byte[]{};
    private String country;
    private double totalWon;
    private long userId;
    private List<Long> tournamentsIds;
    private List<Long> gamesIds;

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

    public List<Long> getTournamentsIds() {
        return tournamentsIds;
    }

    public void setTournamentsIds(List<Long> tournamentsIds) {
        this.tournamentsIds = tournamentsIds;
    }

    public List<Long> getGamesIds() {
        return gamesIds;
    }

    public void setGamesIds(List<Long> gamesIds) {
        this.gamesIds = gamesIds;
    }

    public static final class Builder {
        private long id;
        private String name;
        private String nickname;
        private String surname;
        private byte[] photo = new byte[]{};
        private String country;
        private double totalWon;
        private long userId;
        private List<Long> tournamentsIds;
        private List<Long> gamesIds;

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

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
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

        public Builder tournamentsIds(List<Long> tournamentsIds) {
            this.tournamentsIds = tournamentsIds;
            return this;
        }

        public Builder gamesIds(List<Long> gamesIds) {
            this.gamesIds = gamesIds;
            return this;
        }

        public PlayerDto build() {
            PlayerDto playerDto = new PlayerDto();
            playerDto.setId(id);
            playerDto.setName(name);
            playerDto.setNickname(nickname);
            playerDto.setSurname(surname);
            playerDto.setPhoto(photo);
            playerDto.setCountry(country);
            playerDto.setTotalWon(totalWon);
            playerDto.setUserId(userId);
            playerDto.setTournamentsIds(tournamentsIds);
            playerDto.setGamesIds(gamesIds);
            return playerDto;
        }
    }
}
