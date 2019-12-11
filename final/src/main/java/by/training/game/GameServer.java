package by.training.game;

import java.util.Date;

public class GameServer {

    private final String path;
    private final int pointsToWin;
    private int playerOnePoints = 0;
    private int playerTwoPoints = 0;
    private Date endTime;

    public GameServer(int pointsToWin) {
        this.path = generatePath();
        this.pointsToWin = pointsToWin;
    }

    private String generatePath() {
        return "fake_path";
    }

    public boolean increaseFirstPlayerPoints() {
        if (playerOnePoints < pointsToWin) {
            playerOnePoints++;
            return true;
        } else if (playerOnePoints == pointsToWin) {
            endTime = new Date();
        }
        return false;
    }

    public boolean increaseSecondPlayerPoints() {
        if (playerTwoPoints < pointsToWin) {
            playerTwoPoints++;
            return true;
        } else if (playerTwoPoints == pointsToWin) {
            endTime = new Date();
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
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
