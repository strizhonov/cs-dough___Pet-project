package by.training.tournament;

import java.io.Serializable;
import java.util.Objects;

public class TournamentValidationDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long logoSize;
    private String logoType;
    private String name;
    private String organizerRewardPercentage;
    private String fromOrganizerBonus;
    private String buyIn;
    private String playersNumber;


    public TournamentValidationDto() {
    }


    public TournamentValidationDto(long logoSize, String logoType, String name, String organizerRewardPercentage,
                                   String fromOrganizerBonus, String buyIn, String playersNumber) {
        this.logoSize = logoSize;
        this.logoType = logoType;
        this.name = name;
        this.organizerRewardPercentage = organizerRewardPercentage;
        this.fromOrganizerBonus = fromOrganizerBonus;
        this.buyIn = buyIn;
        this.playersNumber = playersNumber;
    }


    public long getLogoSize() {
        return logoSize;
    }

    public void setLogoSize(long logoSize) {
        this.logoSize = logoSize;
    }

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(String logoType) {
        this.logoType = logoType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizerRewardPercentage() {
        return organizerRewardPercentage;
    }

    public void setOrganizerRewardPercentage(String organizerRewardPercentage) {
        this.organizerRewardPercentage = organizerRewardPercentage;
    }

    public String getFromOrganizerBonus() {
        return fromOrganizerBonus;
    }

    public void setFromOrganizerBonus(String fromOrganizerBonus) {
        this.fromOrganizerBonus = fromOrganizerBonus;
    }

    public String getBuyIn() {
        return buyIn;
    }

    public void setBuyIn(String buyIn) {
        this.buyIn = buyIn;
    }

    public String getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(String playersNumber) {
        this.playersNumber = playersNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentValidationDto that = (TournamentValidationDto) o;
        return logoSize == that.logoSize &&
                Objects.equals(logoType, that.logoType) &&
                Objects.equals(name, that.name) &&
                Objects.equals(organizerRewardPercentage, that.organizerRewardPercentage) &&
                Objects.equals(fromOrganizerBonus, that.fromOrganizerBonus) &&
                Objects.equals(buyIn, that.buyIn) &&
                Objects.equals(playersNumber, that.playersNumber);
    }


    @Override
    public int hashCode() {
        return Objects.hash(logoSize, logoType, name, organizerRewardPercentage, fromOrganizerBonus, buyIn, playersNumber);
    }


    @Override
    public String toString() {
        return "TournamentValidationDto{" +
                "logoSize=" + logoSize +
                ", logoType='" + logoType + '\'' +
                ", name='" + name + '\'' +
                ", organizerRewardPercentage='" + organizerRewardPercentage + '\'' +
                ", fromOrganizerBonus='" + fromOrganizerBonus + '\'' +
                ", buyIn='" + buyIn + '\'' +
                ", playersNumber='" + playersNumber + '\'' +
                '}';
    }

}
