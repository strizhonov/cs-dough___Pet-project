package by.training.game;

import java.util.Date;
import java.util.Objects;

public class GameServerModel {

    private final String path;
    private int pointsToWin;
    private int playerOnePoints;
    private int playerTwoPoints;
    private Date endTime;
    private boolean isStarted;


    public GameServerModel(int pointsToWin) {
        this.path = generatePath();
        this.pointsToWin = pointsToWin;
    }


    public GameServerModel(GameServerDto gameServerDto) {
        this.path = gameServerDto.getPath();
        this.pointsToWin = gameServerDto.getPointsToWin();
        this.playerOnePoints = gameServerDto.getPlayerOnePoints();
        this.playerTwoPoints = gameServerDto.getPlayerTwoPoints();
    }


    private String generatePath() {
        return "fake_path";
    }


    public void startIfNot() {
        isStarted = true;
    }

    /**
     * Implements logic of winning round by the first player. Increases its
     * count by one.
     *
     * @return false if increased points reached win points, indicating that the
     * game can not be continued. Otherwise returns true.
     */
    public boolean increaseFirstPlayerPoints() throws ServerIsNotReadyException {
        if (!isStarted) {
            throw new ServerIsNotReadyException("Server is not ready to start.");
        }

        if (playerOnePoints < pointsToWin && playerTwoPoints < pointsToWin) {
            playerOnePoints++;
            if (playerOnePoints == pointsToWin) {
                endTime = new Date();
                return true;
            }

        }

        return false;
    }


    /**
     * Implements logic of winning round by the second player. Increases its
     * count by one.
     *
     * @return false if increased points reached win points, indicating that the
     * game can not be continued. Otherwise returns true.
     */
    public boolean increaseSecondPlayerPoints() throws ServerIsNotReadyException {
        if (!isStarted) {
            throw new ServerIsNotReadyException("Server is not ready to start.");
        }

        if (playerOnePoints < pointsToWin && playerTwoPoints < pointsToWin) {
            playerTwoPoints++;
            if (playerTwoPoints == pointsToWin) {
                endTime = new Date();
                return true;
            }

        }

        return false;
    }


    public String getPath() {
        return path;
    }

    public int getPointsToWin() {
        return pointsToWin;
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

    public Date getEndTime() {
        return new Date(endTime.getTime());
    }

    public void setEndTime(Date endTime) {
        this.endTime = new Date(endTime.getTime());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameServerModel that = (GameServerModel) o;
        return pointsToWin == that.pointsToWin &&
                playerOnePoints == that.playerOnePoints &&
                playerTwoPoints == that.playerTwoPoints &&
                Objects.equals(path, that.path) &&
                Objects.equals(endTime, that.endTime);
    }


    @Override
    public int hashCode() {
        return Objects.hash(path, pointsToWin, playerOnePoints, playerTwoPoints, endTime);
    }


    @Override
    public String toString() {
        return "GameServerModel{" +
                "path='" + path + '\'' +
                ", pointsToWin=" + pointsToWin +
                ", playerOnePoints=" + playerOnePoints +
                ", playerTwoPoints=" + playerTwoPoints +
                ", endTime=" + endTime +
                '}';
    }

}
