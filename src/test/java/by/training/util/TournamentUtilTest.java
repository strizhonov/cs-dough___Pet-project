package by.training.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

@RunWith(JUnit4.class)
public class TournamentUtilTest {

    @Test
    public void getNextGameIndex() {
        Assert.assertEquals(TournamentUtil.getNextGameIndex(0), -1);
        Assert.assertEquals(0, TournamentUtil.getNextGameIndex(1));
        Assert.assertEquals(0, TournamentUtil.getNextGameIndex(2));
        Assert.assertEquals(1, TournamentUtil.getNextGameIndex(3));
        Assert.assertEquals(1, TournamentUtil.getNextGameIndex(4));
    }


    @Test
    public void calculatePrizePool() {
        Assert.assertEquals(0, TournamentUtil.calculatePrizePool(1, 0, 1, 10), 0.0);
        Assert.assertEquals(0, TournamentUtil.calculatePrizePool(0, 0, 0, 0), 0.0);
        Assert.assertEquals(100, TournamentUtil.calculatePrizePool(0, 100, 0, 0), 0.0);
        Assert.assertEquals(100, TournamentUtil.calculatePrizePool(0.5, 0, 20, 10), 0.0);
    }


    @Test
    public void calculateFromOrganizerBonus() {
        Assert.assertEquals(0, TournamentUtil.calculateFromOrganizerBonus(0, 0, 0, 0), 0.0);
        Assert.assertEquals(50, TournamentUtil.calculateFromOrganizerBonus(0, 100, 10, 5), 0.0);
        Assert.assertEquals(75, TournamentUtil.calculateFromOrganizerBonus(0.5, 100, 10, 5), 0.0);
    }


    @Test
    public void calculateOrganizerReward() {
        Assert.assertEquals(0, TournamentUtil.calculateOrganizerReward(0, 0, 0), 0.0);
        Assert.assertEquals(50, TournamentUtil.calculateOrganizerReward(0.5, 10, 10), 0.0);

    }


    @Test
    public void getGameAutoTime() throws InterruptedException {
        Date checkPointOne = new Date();
        Thread.sleep(10);

        Date dateOne = TournamentUtil.getGameAutoTime(0, 0);
        Thread.sleep(10);

        Date checkPointTwo = new Date();

        Assert.assertTrue(dateOne.after(checkPointOne));
        Assert.assertTrue(dateOne.before(checkPointTwo));


        Date dateTwo = TournamentUtil.getGameAutoTime(1000, 0);
        Thread.sleep(10);

        Date checkPointThree = new Date();

        Assert.assertTrue(dateTwo.after(checkPointThree));


        Date dateThree = TournamentUtil.getGameAutoTime(100, 2);
        Thread.sleep(10);
        Date checkPointFour = new Date();

        Assert.assertTrue(dateThree.after(checkPointFour));

    }


}
