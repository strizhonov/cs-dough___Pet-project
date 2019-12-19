package by.training.util;

import java.util.Date;

public class TournamentUtil {


    public static int getNextGameIndex(int currentGameIndex) {

        if (currentGameIndex == 0) {
            return -1;
        } else {
            return (currentGameIndex - 1) / 2;
        }

    }


    public static double calculatePrizePool(double rewardRate, double bonus, double buyIn, int playersNumber) {

        return buyIn * playersNumber + bonus - rewardRate * buyIn * playersNumber;

    }


    public static double calculateFromOrganizerBonus(double rewardRate, double prize, double buyIn, int playersNumber) {

        return rewardRate * buyIn * playersNumber + prize - buyIn * playersNumber;

    }


    public static double calculateOrganizerReward(double rewardRate, double buyIn, int playersNumber) {

        return buyIn * playersNumber * rewardRate;

    }


    public static Date getGameAutoTime(long intervalMs, int gameIndex) {

        return new Date(System.currentTimeMillis() + (gameIndex + 1) * intervalMs);

    }

}
