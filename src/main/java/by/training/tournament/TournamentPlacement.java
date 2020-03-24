package by.training.tournament;

import by.training.player.PlayerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class corresponds to tournament result line, which has
 * <p>
 * - placementNumber - from 1 to participant number
 * <p>
 * - playersOnPosition - if tournament is not finished there can be more than one
 * participant on current placement, but then other placement should be empty.
 * <p>
 * - finished - status of placement. finished == true indicates that placement should contain
 * no more than one participant.
 * <p>
 * - prize - related for the placement prize
 *
 * @author Uladzislau Stryzhonak
 */
public class TournamentPlacement {

    private final int placementNumber;
    private List<PlayerDto> playersOnPosition = new ArrayList<>();
    private boolean finished;
    private double prize;


    public TournamentPlacement(int placementNumber) {
        this.placementNumber = placementNumber;
    }


    public void addPlayer(PlayerDto player) {
        playersOnPosition.add(player);
    }

    public int getPlacementNumber() {
        return placementNumber;
    }

    public List<PlayerDto> getPlayersOnPosition() {
        return playersOnPosition;
    }

    public void setPlayersOnPosition(List<PlayerDto> playersOnPosition) {
        this.playersOnPosition = playersOnPosition;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentPlacement placement = (TournamentPlacement) o;
        return placementNumber == placement.placementNumber &&
                finished == placement.finished &&
                Double.compare(placement.prize, prize) == 0 &&
                Objects.equals(playersOnPosition, placement.playersOnPosition);
    }


    @Override
    public int hashCode() {
        return Objects.hash(placementNumber, playersOnPosition, finished, prize);
    }


    @Override
    public String toString() {
        return "TournamentPlacement{" +
                "placementNumber=" + placementNumber +
                ", playersOnPosition=" + playersOnPosition +
                ", finished=" + finished +
                ", prize=" + prize +
                '}';
    }


}
