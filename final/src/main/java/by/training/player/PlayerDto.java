package by.training.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerDto extends PlainPlayerDto {

    private static final long serialVersionUID = 4L;

    private List<Long> tournamentsIds = new ArrayList<>();
    private List<Long> gamesIds = new ArrayList<>();


    public PlayerDto() {
    }


    public PlayerDto(long id, String name, String nickname, String surname, byte[] photo, double totalWon,
                     long userId, List<Long> tournamentsIds, List<Long> gamesIds) {
        super(id, name, nickname, surname, photo, totalWon, userId);
        this.tournamentsIds = tournamentsIds;
        this.gamesIds = gamesIds;
    }


    private PlayerDto(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setNickname(builder.nickname);
        setSurname(builder.surname);
        setPhoto(builder.photo);
        setTotalWon(builder.totalWon);
        setUserId(builder.userId);
        setTournamentsIds(builder.tournamentsIds);
        setGamesIds(builder.gamesIds);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PlayerDto playerDto = (PlayerDto) o;
        return Objects.equals(tournamentsIds, playerDto.tournamentsIds) &&
                Objects.equals(gamesIds, playerDto.gamesIds);
    }


    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hash(tournamentsIds, gamesIds);
    }


    @Override
    public String toString() {
        return "PlayerDto{" +
                "tournamentsIds=" + tournamentsIds +
                ", gamesIds=" + gamesIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", surname='" + surname + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", totalWon=" + totalWon +
                ", userId=" + userId +
                '}';
    }


    public static final class Builder {
        private long id;
        private String name;
        private String nickname;
        private String surname;
        private byte[] photo;
        private double totalWon;
        private long userId;
        private List<Long> tournamentsIds;
        private List<Long> gamesIds;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder nickname(String val) {
            nickname = val;
            return this;
        }

        public Builder surname(String val) {
            surname = val;
            return this;
        }

        public Builder photo(byte[] val) {
            if (val == null) {
                this.photo = null;
            } else {
                this.photo = Arrays.copyOf(val, val.length);
            }
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

        public Builder tournamentsIds(List<Long> val) {
            tournamentsIds = val;
            return this;
        }

        public Builder gamesIds(List<Long> val) {
            gamesIds = val;
            return this;
        }

        public PlayerDto build() {
            return new PlayerDto(this);
        }
    }
}
