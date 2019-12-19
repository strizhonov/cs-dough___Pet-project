package by.training.game;

import by.training.core.Entity;

import java.util.Objects;

public class GameServer extends Entity {

    private static final long serialVersionUID = 4L;

    private int pointsToWin;
    private int playerOnePoints;
    private int playerTwoPoints;
    private String path;
    private long gameId;

    public GameServer() {
    }


    public GameServer(int pointsToWin, int playerOnePoints, int playerTwoPoints, String path, long gameId) {
        this.pointsToWin = pointsToWin;
        this.playerOnePoints = playerOnePoints;
        this.playerTwoPoints = playerTwoPoints;
        this.path = path;
        this.gameId = gameId;
    }


    private GameServer(Builder builder) {
        setId(builder.id);
        setPointsToWin(builder.pointsToWin);
        setPlayerOnePoints(builder.playerOnePoints);
        setPlayerTwoPoints(builder.playerTwoPoints);
        setPath(builder.path);
        setGameId(builder.gameId);
    }


    public int getPointsToWin() {
        return pointsToWin;
    }

    public void setPointsToWin(int pointsToWin) {
        this.pointsToWin = pointsToWin;
    }

    public int getPlayerOnePoints() {
        return playerOnePoints;
    }

    public void setPlayerOnePoints(int playerOnePoints) {
        this.playerOnePoints = playerOnePoints;
    }

    public int getPlayerTwoPoints() {
        return playerTwoPoints;
    }

    public void setPlayerTwoPoints(int playerTwoPoints) {
        this.playerTwoPoints = playerTwoPoints;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameServer that = (GameServer) o;
        return pointsToWin == that.pointsToWin &&
                playerOnePoints == that.playerOnePoints &&
                playerTwoPoints == that.playerTwoPoints &&
                gameId == that.gameId &&
                Objects.equals(path, that.path);
    }


    @Override
    public int hashCode() {
        return Objects.hash(pointsToWin, playerOnePoints, playerTwoPoints, path, gameId);
    }


    @Override
    public String toString() {
        return "GameServerEntity{" +
                "pointsToWin=" + pointsToWin +
                ", playerOnePoints=" + playerOnePoints +
                ", playerTwoPoints=" + playerTwoPoints +
                ", path='" + path + '\'' +
                ", gameId=" + gameId +
                ", id=" + id +
                '}';
    }


    public static final class Builder {
        private long id;
        private int pointsToWin;
        private int playerOnePoints;
        private int playerTwoPoints;
        private String path;
        private long gameId;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder pointsToWin(int val) {
            pointsToWin = val;
            return this;
        }

        public Builder playerOnePoints(int val) {
            playerOnePoints = val;
            return this;
        }

        public Builder playerTwoPoints(int val) {
            playerTwoPoints = val;
            return this;
        }

        public Builder path(String val) {
            path = val;
            return this;
        }

        public Builder gameId(long val) {
            gameId = val;
            return this;
        }

        public GameServer build() {
            return new GameServer(this);
        }
    }

}
