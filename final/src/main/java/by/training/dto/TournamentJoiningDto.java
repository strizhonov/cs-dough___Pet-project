package by.training.dto;

public class TournamentJoiningDto {

    private long playerId;
    private long tournamentId;

    public long getPlayerId() {
        return playerId;
    }

    public TournamentJoiningDto() {
    }

    public TournamentJoiningDto(long playerId, long tournamentId) {
        this.playerId = playerId;
        this.tournamentId = tournamentId;
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
}
