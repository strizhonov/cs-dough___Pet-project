package by.training.game;

import by.training.player.PlainPlayerDto;
import by.training.player.PlayerDto;
import by.training.tournament.PlainTournamentDto;
import by.training.tournament.TournamentDto;

import java.util.Date;
import java.util.Objects;

public class ComplexGameDto extends PlainGameDto {

    private static final long serialVersionUID = 4L;

    private PlainPlayerDto firstPlayer;
    private PlainPlayerDto secondPlayer;
    private GameServerDto gameServer;
    private PlainTournamentDto tournament;


    public ComplexGameDto() {
    }


    private ComplexGameDto(Builder builder) {
        setId(builder.id);
        setBracketIndex(builder.bracketIndex);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setFirstPlayer(builder.firstPlayer);
        setSecondPlayer(builder.secondPlayer);
        setGameServer(builder.gameServer);
        setTournament(builder.tournament);
    }


    public PlainPlayerDto getFirstPlayer() {
        return firstPlayer;
    }


    public void setFirstPlayer(PlainPlayerDto firstPlayer) {
        this.firstPlayerId = firstPlayer.getId();
        this.firstPlayer = firstPlayer;
    }


    public PlainPlayerDto getSecondPlayer() {
        return secondPlayer;
    }


    public void setSecondPlayer(PlainPlayerDto secondPlayer) {
        this.secondPlayerId = secondPlayer.getId();
        this.secondPlayer = secondPlayer;
    }


    public GameServerDto getGameServer() {
        return gameServer;
    }


    public void setGameServer(GameServerDto gameServer) {
        this.gameServerId = gameServer.getId();
        this.gameServer = gameServer;
    }


    public PlainTournamentDto getTournament() {
        return tournament;
    }


    public void setTournament(PlainTournamentDto tournament) {
        this.tournamentId = tournament.getId();
        this.tournament = tournament;
    }


    /**
     * Gets winner player according to players' points and points to win.
     *
     * @return null if game is not finished so there is no winner
     */
    public PlainPlayerDto getWinner() {
        if (gameServer.getPlayerOnePoints() == gameServer.getPointsToWin()) {
            return firstPlayer;
        }

        if (gameServer.getPlayerTwoPoints() == gameServer.getPointsToWin()) {
            return secondPlayer;
        }

        return null;
    }


    /**
     * Gets looser player (opposite to winner player).
     *
     * @return null if winner is null.
     */
    public PlainPlayerDto getLooser() {
        PlainPlayerDto winner = getWinner();
        if (winner == null) {
            return null;
        }

        if (winner.equals(firstPlayer)) {
            return secondPlayer;
        } else {
            return firstPlayer;
        }
    }


    /**
     * Sets player and its id to the first found empty slot.
     *
     * @param player to set entity.
     * @return slot order number. If there is no empty slot returns -1.
     */
    @Override
    public int occupyPlayerSlot(PlainPlayerDto player) {
        if (firstPlayerId == 0) {
            firstPlayerId = player.getId();
            firstPlayer = player;
            return 0;
        }

        if (secondPlayerId == 0) {
            secondPlayerId = player.getId();
            secondPlayer = player;
            return 1;
        }

        return -1;
    }

    /**
     * Clear player from corresponding slot.
     *
     * @param player to clear from slot.
     * @return cleared slot number. If there is no such player on both slots, returns -1.
     */
    @Override
    public int releasePlayerSlot(PlayerDto player) {
        if (firstPlayerId == player.getId()) {
            firstPlayerId = 0;
            firstPlayer = null;
            return 0;
        }

        if (secondPlayerId == player.getId()) {
            secondPlayerId = 0;
            secondPlayer = null;
            return 0;
        }

        return -1;
    }


    /**
     * Indicate if game is ready to start.
     *
     * @return true if game has both players. If there is no at least one player, return false
     */
    public boolean isReady() {
        return firstPlayer != null && secondPlayer != null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexGameDto that = (ComplexGameDto) o;
        return Objects.equals(firstPlayer, that.firstPlayer) &&
                Objects.equals(secondPlayer, that.secondPlayer) &&
                Objects.equals(gameServer, that.gameServer) &&
                Objects.equals(tournament, that.tournament);
    }


    @Override
    public int hashCode() {
        return Objects.hash(firstPlayer, secondPlayer, gameServer, tournament);
    }


    @Override
    public String toString() {
        return "ComplexGameDto{" +
                "firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", gameServer=" + gameServer +
                ", tournament=" + tournament +
                ", id=" + id +
                ", bracketIndex=" + bracketIndex +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", firstPlayerId=" + firstPlayerId +
                ", secondPlayerId=" + secondPlayerId +
                ", tournamentId=" + tournamentId +
                ", gameServerId=" + gameServerId +
                '}';
    }


    public static final class Builder {
        private long id;
        private int bracketIndex;
        private Date startTime;
        private Date endTime;
        private PlainPlayerDto firstPlayer;
        private PlainPlayerDto secondPlayer;
        private GameServerDto gameServer;
        private PlainTournamentDto tournament;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder bracketIndex(int val) {
            bracketIndex = val;
            return this;
        }

        public Builder startTime(Date startTime) {
            if (startTime == null) {
                this.startTime = null;
            } else {
                this.startTime = new Date(startTime.getTime());
            }
            return this;
        }

        public Builder endTime(Date endTime) {
            if (endTime == null) {
                this.endTime = null;
            } else {
                this.endTime = new Date(endTime.getTime());
            }
            return this;
        }

        public Builder firstPlayer(PlainPlayerDto val) {
            firstPlayer = val;
            return this;
        }

        public Builder secondPlayer(PlainPlayerDto val) {
            secondPlayer = val;
            return this;
        }

        public Builder gameServer(GameServerDto val) {
            gameServer = val;
            return this;
        }

        public Builder tournament(TournamentDto val) {
            tournament = val;
            return this;
        }

        public ComplexGameDto build() {
            return new ComplexGameDto(this);
        }
    }

}



