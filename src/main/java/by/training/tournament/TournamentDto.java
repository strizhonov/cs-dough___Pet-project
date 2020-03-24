package by.training.tournament;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TournamentDto extends PlainTournamentDto {

    private static final long serialVersionUID = 4L;

    private List<Long> gamesIds = new ArrayList<>();
    private List<Long> participantsIds = new ArrayList<>();


    public TournamentDto() {
    }


    public TournamentDto(long id, String name, byte[] logo, double prizePool, double reward, double buyIn,
                         int playersNumber, Tournament.TournamentStatus status, long organizerId,
                         List<Long> gamesIds, List<Long> participantsIds) {
        super(id, name, logo, prizePool, reward, buyIn, playersNumber, status, organizerId);
        this.gamesIds = gamesIds;
        this.participantsIds = participantsIds;
    }


    private TournamentDto(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setLogo(builder.logo);
        setPrizePool(builder.prizePool);
        setOrganizerRewardPercentage(builder.reward);
        setBuyIn(builder.buyIn);
        setPlayersNumber(builder.playersNumber);
        setStatus(builder.status);
        setOrganizerId(builder.organizerId);
        setGamesIds(builder.gamesIds);
        setParticipantsIds(builder.participantsIds);
    }


    public List<Long> getGamesIds() {
        return gamesIds;
    }

    public void addGameId(long gameId) {
        gamesIds.add(gameId);
    }

    public void setGamesIds(List<Long> gamesIds) {
        this.gamesIds = gamesIds;
    }

    public List<Long> getParticipantsIds() {
        return participantsIds;
    }

    public void setParticipantsIds(List<Long> participantsIds) {
        this.participantsIds = participantsIds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TournamentDto that = (TournamentDto) o;
        return Objects.equals(gamesIds, that.gamesIds) &&
                Objects.equals(participantsIds, that.participantsIds);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gamesIds, participantsIds);
    }


    @Override
    public String toString() {
        return "TournamentDto{" +
                "gamesIds=" + gamesIds +
                ", participantsIds=" + participantsIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", logo=" + Arrays.toString(logo) +
                ", prizePool=" + prizePool +
                ", organizerRewardPercentage=" + organizerRewardPercentage +
                ", buyIn=" + buyIn +
                ", playersNumber=" + playersNumber +
                ", status=" + status +
                ", organizerId=" + organizerId +
                '}';
    }


    public static final class Builder {
        private long id;
        private String name;
        private byte[] logo = new byte[0];
        private double prizePool;
        private double reward;
        private double buyIn;
        private int playersNumber;
        private Tournament.TournamentStatus status = Tournament.TournamentStatus.getDefault();
        private long organizerId;
        private List<Long> gamesIds = new ArrayList<>();
        private List<Long> participantsIds = new ArrayList<>();

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

        public Builder logo(byte[] val) {
            if (val == null) {
                this.logo = null;
            } else {
                this.logo = Arrays.copyOf(val, val.length);
            }
            return this;
        }

        public Builder prizePool(double val) {
            prizePool = val;
            return this;
        }

        public Builder reward(double val) {
            reward = val;
            return this;
        }

        public Builder buyIn(double val) {
            buyIn = val;
            return this;
        }

        public Builder playersNumber(int val) {
            playersNumber = val;
            return this;
        }

        public Builder status(Tournament.TournamentStatus val) {
            status = val;
            return this;
        }

        public Builder organizerId(long val) {
            organizerId = val;
            return this;
        }

        public Builder gamesIds(List<Long> val) {
            gamesIds = val;
            return this;
        }

        public Builder participantsIds(List<Long> val) {
            participantsIds = val;
            return this;
        }

        public TournamentDto build() {
            return new TournamentDto(this);
        }
    }
}
