package io.himoura;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

        RaceCondition raceCondition = new RaceCondition();
        boolean result = raceCondition.addGuest("sasuke");

        Assert.assertTrue(result);

    }
    @Test
    public void shouldAnswerWithFalse()
    {

        RaceCondition raceCondition = new RaceCondition();
        boolean result = raceCondition.addGuest("naruto");

        Assert.assertFalse(result);
    }

    @Test
    public void shouldDuplicateGuest()
    {

        final RaceCondition raceCondition = new RaceCondition();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(()->raceCondition.addGuest("jira"));
        executorService.execute(()->raceCondition.addGuest("jira"));
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Long counterJira = RaceCondition.guests.stream().filter(g->g.equals("jira")).count();

        Assert.assertTrue(counterJira == 2);
    }

    @Test
    public void shouldUniqueGuest()
    {

        final RaceCondition raceCondition = new RaceCondition();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(()->raceCondition.addGuestThreadSafe("kakashi"));
        executorService.execute(()->raceCondition.addGuestThreadSafe("kakashi"));
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Long counterJira = RaceCondition.guests.stream().filter(g->g.equals("kakashi")).count();

        Assert.assertTrue(counterJira == 1);
    }


}
