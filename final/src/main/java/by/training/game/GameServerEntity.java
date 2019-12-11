package by.training.game;

import by.training.common.Entity;

public class GameServerEntity extends Entity {

    private int pointsToWin;
    private int playerOnePoints;
    private int playerTwoPoints;
    private String path;
    private long gameId;

    public GameServerEntity() {
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


    public static final class Builder {
        private long id;
        private int pointsToWin;
        private int playerOnePoints;
        private int playerTwoPoints;
        private String path;
        private long gameId;

        public Builder() {
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder pointsToWin(int pointsToWin) {
            this.pointsToWin = pointsToWin;
            return this;
        }

        public Builder playerOnePoints(int playerOnePoints) {
            this.playerOnePoints = playerOnePoints;
            return this;
        }

        public Builder playerTwoPoints(int playerTwoPoints) {
            this.playerTwoPoints = playerTwoPoints;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder gameId(long gameId) {
            this.gameId = gameId;
            return this;
        }

        public GameServerEntity build() {
            GameServerEntity gameServerEntity = new GameServerEntity();
            gameServerEntity.setId(id);
            gameServerEntity.setPointsToWin(pointsToWin);
            gameServerEntity.setPlayerOnePoints(playerOnePoints);
            gameServerEntity.setPlayerTwoPoints(playerTwoPoints);
            gameServerEntity.setPath(path);
            gameServerEntity.setGameId(gameId);
            return gameServerEntity;
        }
    }
}
