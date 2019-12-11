package by.training.tournament;

import by.training.core.AppSetting;
import by.training.common.Entity;
import by.training.core.ApplicationContext;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public class Tournament extends Entity {

    private String name;
    private byte[] logo = new byte[0];
    private double prizePool;
    private double buiIn;
    private int playersNumber;
    private Date startDate;
    private Date endDate;
    private TournamentStatus status = TournamentStatus.getDefault();
    private long organizerId;

    public enum TournamentStatus {
        UPCOMING, ONGOING, FINISHED;

        public static TournamentStatus getDefault() {
            AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
            return fromString(setting.get(AppSetting.SettingName.DEFAULT_TOURNAMENT_STATUS)).orElse(null);
        }

        public static Optional<TournamentStatus> fromString(String type) {
            return Stream.of(TournamentStatus.values())
                    .filter(t -> t.name().equalsIgnoreCase(type))
                    .findFirst();
        }
    }

    public double getBuiIn() {
        return buiIn;
    }

    public void setBuiIn(double buiIn) {
        this.buiIn = buiIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public double getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(double prizePool) {
        this.prizePool = prizePool;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public static final class Builder {

        private long id;
        private String name;
        private byte[] logo = new byte[0];
        private double prizePool;
        private double buiIn;
        private int playersNumber;
        private Date startDate;
        private Date endDate;
        private TournamentStatus status = TournamentStatus.getDefault();
        private long organizerId;

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

        public Builder logo(byte[] logo) {
            this.logo = logo;
            return this;
        }

        public Builder prizePool(double prizePool) {
            this.prizePool = prizePool;
            return this;
        }

        public Builder buiIn(double buiIn) {
            this.buiIn = buiIn;
            return this;
        }

        public Builder playersNumber(int playersNumber) {
            this.playersNumber = playersNumber;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder status(TournamentStatus status) {
            this.status = status;
            return this;
        }

        public Builder organizerId(long organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public Tournament build() {
            Tournament tournament = new Tournament();
            tournament.setId(id);
            tournament.setName(name);
            tournament.setLogo(logo);
            tournament.setPrizePool(prizePool);
            tournament.setBuiIn(buiIn);
            tournament.setPlayersNumber(playersNumber);
            tournament.setStartDate(startDate);
            tournament.setEndDate(endDate);
            tournament.setStatus(status);
            tournament.setOrganizerId(organizerId);
            return tournament;
        }
    }
}
