package by.training.tournament;

import by.training.common.BaseDto;
import by.training.core.AppSetting;
import by.training.core.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TournamentDto extends BaseDto {

    private long id;
    private String name;
    private byte[] logo;
    private double prizePool;
    private double buyIn;
    private int playersNumber;
    private Date startDate;
    private Date endDate;
    private Tournament.TournamentStatus status;
    private long organizerId;
    private List<Long> gamesIds = new ArrayList<>();
    private List<Long> participantsIds = new ArrayList<>();

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

    public double getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(double buyIn) {
        this.buyIn = buyIn;
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

    public Tournament.TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(Tournament.TournamentStatus status) {
        this.status = status;
    }

    public long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(long organizerId) {
        this.organizerId = organizerId;
    }

    public List<Long> getGamesIds() {
        return gamesIds;
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

    public static final class Builder {

        protected final AppSetting setting = (AppSetting) ApplicationContext.getInstance().get(AppSetting.class);
        private long id;
        private String name;
        private byte[] logo;
        private double prizePool;
        private double buiIn;
        private int playersNumber;
        private Date startDate;
        private Date endDate;
        private Tournament.TournamentStatus status = Tournament.TournamentStatus
                .fromString(setting.get(AppSetting.SettingName.DEFAULT_TOURNAMENT_STATUS))
                .orElse(null);
        private long organizerId;
        private List<Long> gamesIds = new ArrayList<>();
        private List<Long> participantsIds = new ArrayList<>();

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

        public Builder status(Tournament.TournamentStatus status) {
            this.status = status;
            return this;
        }

        public Builder organizerId(long organizerId) {
            this.organizerId = organizerId;
            return this;
        }

        public Builder gamesIds(List<Long> gamesIds) {
            this.gamesIds = gamesIds;
            return this;
        }

        public Builder participantsIds(List<Long> participantsIds) {
            this.participantsIds = participantsIds;
            return this;
        }

        public TournamentDto build() {
            TournamentDto tournamentDto = new TournamentDto();
            tournamentDto.setId(id);
            tournamentDto.setName(name);
            tournamentDto.setLogo(logo);
            tournamentDto.setPrizePool(prizePool);
            tournamentDto.setBuyIn(buiIn);
            tournamentDto.setPlayersNumber(playersNumber);
            tournamentDto.setStartDate(startDate);
            tournamentDto.setEndDate(endDate);
            tournamentDto.setStatus(status);
            tournamentDto.setOrganizerId(organizerId);
            tournamentDto.setGamesIds(gamesIds);
            tournamentDto.setParticipantsIds(participantsIds);
            return tournamentDto;
        }
    }
}
