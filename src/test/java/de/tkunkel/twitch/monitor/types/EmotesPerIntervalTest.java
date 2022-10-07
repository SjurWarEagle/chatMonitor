package de.tkunkel.twitch.monitor.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

class EmotesPerIntervalTest {

    @Test
    public void testRemoveObsoleteEntries() throws InterruptedException {
        EmotesPerInterval emotesPerInterval = new EmotesPerInterval(100, TimeUnit.MILLISECONDS);
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
        EmotesPerInterval emotesPerInterval = new EmotesPerInterval(100, TimeUnit.MILLISECONDS);
        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        Thread.sleep(10);
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        Thread.sleep(200);
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        Thread.sleep(200);
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);
    }

    @Test
    public void testGetNumberOfEmotesInInterval_withWait_and_multiple() throws InterruptedException {
        EmotesPerInterval emotesPerInterval = new EmotesPerInterval(100, TimeUnit.MILLISECONDS);
        Integer numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(0, numberOfEmotesInInterval);

        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);

        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(50);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(2, numberOfEmotesInInterval);

        Thread.sleep(200);
        emotesPerInterval.rememberEmote("A");
        numberOfEmotesInInterval = emotesPerInterval.getNumberOfEmotesInInterval("A");
        Assertions.assertEquals(1, numberOfEmotesInInterval);
    }

    @Test
    public void testGetNumberOfEmotesInInterval_noWait() {
        EmotesPerInterval emotesPerInterval = new EmotesPerInterval(2, TimeUnit.SECONDS);

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
