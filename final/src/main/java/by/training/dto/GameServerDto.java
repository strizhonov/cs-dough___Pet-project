package by.training.dto;

public class GameServerDto {

    private long id;
    private int pointsToWin;
    private int playerOnePoints;
    private int playerTwoPoints;
    private String path;
    private long gameId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

        private Builder() {
        }

        public static Builder aGameServerDto() {
            return new Builder();
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

        public GameServerDto build() {
            GameServerDto gameServerDto = new GameServerDto();
            gameServerDto.setId(id);
            gameServerDto.setPointsToWin(pointsToWin);
            gameServerDto.setPlayerOnePoints(playerOnePoints);
            gameServerDto.setPlayerTwoPoints(playerTwoPoints);
            gameServerDto.setPath(path);
            gameServerDto.setGameId(gameId);
            return gameServerDto;
        }
    }
}
