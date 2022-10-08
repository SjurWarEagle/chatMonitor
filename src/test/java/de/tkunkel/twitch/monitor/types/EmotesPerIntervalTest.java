package de.tkunkel.twitch.monitor.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class EmotesPerIntervalTest {

    @Test
    public void testRememberAndRead() {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(2, 100, TimeUnit.MILLISECONDS, 1_000, TimeUnit.MILLISECONDS);
        emotesPerInterval.rememberEmote("A");
        int numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);
    }

    @Test
    public void testRemoveObsoleteEntries() throws InterruptedException {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(2, 100, TimeUnit.MILLISECONDS, 1_000, TimeUnit.MILLISECONDS);
        emotesPerInterval.rememberEmote("A");
        emotesPerInterval.removeObsoleteEntries("A");
        int numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);
        Thread.sleep(200);
        emotesPerInterval.removeObsoleteEntries("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);
    }

    @Test
    public void testGetNumberOfEmotesInInterval_withWait() throws InterruptedException {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(10, 100, TimeUnit.MILLISECONDS, 1_000, TimeUnit.MILLISECONDS);
        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(300);
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(300);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);
    }

    @Test
    public void testAddingAndTrigger() throws InterruptedException {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(3, 100, TimeUnit.MILLISECONDS, 1_000, TimeUnit.MILLISECONDS);
        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        Thread.sleep(150);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        Thread.sleep(10);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(10);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        //fixme test trigger
        Assertions.assertEquals(0, numberOfEmotesInInterval);
    }

    @Test
    public void testTriggerAndAddingDuringCooldown() throws InterruptedException {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(3, 100, TimeUnit.MILLISECONDS, 200, TimeUnit.MILLISECONDS);
        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);
        boolean received = emotesPerInterval.readEventFiredMarkerAndSetToReceived("A");
        Assertions.assertTrue(received);
        //only once triggered?
        received = emotesPerInterval.readEventFiredMarkerAndSetToReceived("A");
        Assertions.assertFalse(received);

        //still cooldown
        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        received = emotesPerInterval.readEventFiredMarkerAndSetToReceived("A");
        Assertions.assertFalse(received);
        Assertions.assertEquals(0, numberOfEmotesInInterval);
    }

    @Test
    public void testTriggerAndAddingAfterCooldown() throws InterruptedException {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(3, 100, TimeUnit.MILLISECONDS, 200, TimeUnit.MILLISECONDS);
        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        //fixme test trigger
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        //after cooldown
        Thread.sleep(250);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        //fixme test trigger
        Assertions.assertEquals(1, numberOfEmotesInInterval);
    }

    @Test
    public void testGetNumberOfEmotesInInterval_noWait() {
        EmotesPerIntervalTrigger emotesPerInterval = new EmotesPerIntervalTrigger(20, 2, TimeUnit.SECONDS, 1_000, TimeUnit.MILLISECONDS);

        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("B");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("B");
        Assertions.assertEquals(1, numberOfEmotesInInterval);
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);
    }
}
