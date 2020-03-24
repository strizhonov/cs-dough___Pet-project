package by.training.tournament;

import by.training.util.TournamentUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class PlainTournamentDto implements Serializable {

    private static final long serialVersionUID = 4L;

    protected long id;
    protected String name;
    protected byte[] logo = new byte[0];
    protected double prizePool;
    protected double organizerRewardPercentage;
    protected double buyIn;
    protected int playersNumber;
    protected Tournament.TournamentStatus status = Tournament.TournamentStatus.getDefault();
    protected long organizerId;


    public PlainTournamentDto() {
    }


    public PlainTournamentDto(long id, String name, byte[] logo, double prizePool, double organizerRewardPercentage,
                              double buyIn, int playersNumber, Tournament.TournamentStatus status, long organizerId) {
        this.id = id;
        this.name = name;
        if (logo == null) {
            this.logo = null;
        } else {
            this.logo = Arrays.copyOf(logo, logo.length);
        }
        this.prizePool = prizePool;
        this.organizerRewardPercentage = organizerRewardPercentage;
        this.buyIn = buyIn;
        this.playersNumber = playersNumber;
        this.status = status;
        this.organizerId = organizerId;
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

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
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

    public double getFromOrganizerBonus() {
        return TournamentUtil.calculateFromOrganizerBonus(organizerRewardPercentage, prizePool, buyIn, playersNumber);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlainTournamentDto that = (PlainTournamentDto) o;
        return id == that.id &&
                Double.compare(that.prizePool, prizePool) == 0 &&
                Double.compare(that.organizerRewardPercentage, organizerRewardPercentage) == 0 &&
                Double.compare(that.buyIn, buyIn) == 0 &&
                playersNumber == that.playersNumber &&
                organizerId == that.organizerId &&
                Objects.equals(name, that.name) &&
                Arrays.equals(logo, that.logo) &&
                status == that.status;
    }


    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, prizePool, organizerRewardPercentage, buyIn, playersNumber, status,
                organizerId);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }


    @Override
    public String toString() {
        return "PlainTournamentDto{" +
                "id=" + id +
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


}
