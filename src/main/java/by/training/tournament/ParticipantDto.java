package by.training.tournament;

import java.io.Serializable;
import java.util.Objects;

public class ParticipantDto implements Serializable {

    private static final long serialVersionUID = 4L;

    private long playerId;
    private long tournamentId;


    public ParticipantDto() {
    }

    public ParticipantDto(long playerId, long tournamentId) {
        this.playerId = playerId;
        this.tournamentId = tournamentId;
    }


    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantDto that = (ParticipantDto) o;
        return playerId == that.playerId &&
                tournamentId == that.tournamentId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(playerId, tournamentId);
    }


    @Override
    public String toString() {
        return "TournamentJoiningDto{" +
                "playerId=" + playerId +
                ", tournamentId=" + tournamentId +
                '}';
    }

}
