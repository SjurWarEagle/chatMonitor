package de.tkunkel.twitch.monitor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmoteDetectorTest {

    @Test
    public void test_empty() {
        EmoteDetector emoteDetector = new EmoteDetector();

        Assertions.assertFalse(emoteDetector.containsEmote("A", ""));
        Assertions.assertFalse(emoteDetector.containsEmote("", "A"));
        Assertions.assertFalse(emoteDetector.containsEmote("", ""));
        Assertions.assertFalse(emoteDetector.containsEmote(null, null));
    }

    @Test
    public void test_simple() {
        EmoteDetector emoteDetector = new EmoteDetector();

        Assertions.assertTrue(emoteDetector.containsEmote("This is a test", "a"));
        Assertions.assertTrue(emoteDetector.containsEmote("This is a test", "a test"));
        Assertions.assertTrue(emoteDetector.containsEmote("This is a test", "this"));
        Assertions.assertTrue(emoteDetector.containsEmote("All 🦕 are cool even 🦖!", "🦕"));

        Assertions.assertFalse(emoteDetector.containsEmote("This is a test", "aa"));
        Assertions.assertFalse(emoteDetector.containsEmote("This is a test", "b"));
        Assertions.assertFalse(emoteDetector.containsEmote("This is a test", "a is"));
    }

}
