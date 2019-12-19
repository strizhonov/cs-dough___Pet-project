package by.training.tournament;

import by.training.core.ApplicationContext;
import by.training.core.Entity;
import by.training.resourse.AppSetting;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Tournament extends Entity {

    private static final long serialVersionUID = 4L;

    private byte[] logo = new byte[0];
    private String name;
    private double prizePool;
    private double organizerRewardPercentage;
    private double buyIn;
    private int participantsNumber;
    private TournamentStatus status = TournamentStatus.getDefault();
    private long organizerId;


    public Tournament() {
    }


    public Tournament(byte[] logo, String name, double prizePool, double organizerRewardPercentage, double buyIn,
                      int participantsNumber, TournamentStatus status, long organizerId) {
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        this.name = name;
        this.prizePool = prizePool;
        this.organizerRewardPercentage = organizerRewardPercentage;
        this.buyIn = buyIn;
        this.participantsNumber = participantsNumber;
        this.status = status;
        this.organizerId = organizerId;
    }

    private Tournament(Builder builder) {
        setId(builder.id);
        setLogo(builder.logo);
        setName(builder.name);
        setPrizePool(builder.prizePool);
        setOrganizerRewardPercentage(builder.reward);
        setBuyIn(builder.buyIn);
        setParticipantsNumber(builder.participantsNumber);
        setStatus(builder.status);
        setOrganizerId(builder.organizerId);
    }


    public enum TournamentStatus {

        UPCOMING, ONGOING, FINISHED;


        public static TournamentStatus getDefault() {

            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);

            if (setting == null) {
                return null;
            }

            return fromString(setting.get(AppSetting.SettingName.DEFAULT_TOURNAMENT_STATUS)).orElse(null);
        }


        public static Optional<TournamentStatus> fromString(String type) {
            return Stream.of(TournamentStatus.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }

    }


    public byte[] getLogo() {
        if (logo == null) {
            return null;
        } else {
            return Arrays.copyOf(logo, logo.length);
        }
    }

    public void setLogo(byte[] logo) {
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(double prizePool) {
        this.prizePool = prizePool;
    }

    public double getOrganizerRewardPercentage() {
        return organizerRewardPercentage;
    }

    public void setOrganizerRewardPercentage(double organizerRewardPercentage) {
        this.organizerRewardPercentage = organizerRewardPercentage;
    }

    public double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(double buyIn) {
        this.buyIn = buyIn;
    }

    public int getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(int participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
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
        Tournament that = (Tournament) o;
        return Double.compare(that.prizePool, prizePool) == 0 &&
                Double.compare(that.organizerRewardPercentage, organizerRewardPercentage) == 0 &&
                Double.compare(that.buyIn, buyIn) == 0 &&
                participantsNumber == that.participantsNumber &&
                organizerId == that.organizerId &&
                Arrays.equals(logo, that.logo) &&
                Objects.equals(name, that.name) &&
                status == that.status;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(name, prizePool, organizerRewardPercentage, buyIn, participantsNumber, status, organizerId);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }


    @Override
    public String toString() {
        return "Tournament{" +
                "logo=" + Arrays.toString(logo) +
                ", name='" + name + '\'' +
                ", prizePool=" + prizePool +
                ", reward=" + organizerRewardPercentage +
                ", buyIn=" + buyIn +
                ", participantsNumber=" + participantsNumber +
                ", status=" + status +
                ", organizerId=" + organizerId +
                ", id=" + id +
                '}';
    }


    public static final class Builder {
        private long id;
        private byte[] logo = new byte[0];
        private String name;
        private double prizePool;
        private double reward;
        private double buyIn;
        private int participantsNumber;
        private TournamentStatus status = TournamentStatus.getDefault();
        private long organizerId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
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

        public Builder name(String val) {
            name = val;
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

        public Builder participantsNumber(int val) {
            participantsNumber = val;
            return this;
        }

        public Builder status(TournamentStatus val) {
            status = val;
            return this;
        }

        public Builder organizerId(long val) {
            organizerId = val;
            return this;
        }

        public Tournament build() {
            return new Tournament(this);
        }
    }
}
