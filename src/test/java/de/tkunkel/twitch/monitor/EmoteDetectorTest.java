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

        Assertions.assertTrue(emoteDetector.containsEmote("i", "This is a test"));
        Assertions.assertTrue(emoteDetector.containsEmote("a", "This is a test"));
        Assertions.assertTrue(emoteDetector.containsEmote("a test", "This is a test"));
        Assertions.assertTrue(emoteDetector.containsEmote("this", "This is a test"));
        Assertions.assertTrue(emoteDetector.containsEmote("a", "[amouranth] nabeeght: CRINGEEEEEEEEEEEEEEEE"));
        Assertions.assertTrue(emoteDetector.containsEmote("🦕", "All 🦕 are cool even 🦖!"));

        Assertions.assertFalse(emoteDetector.containsEmote("aa","This is a test"));
        Assertions.assertFalse(emoteDetector.containsEmote("b","This is a test"));
        Assertions.assertFalse(emoteDetector.containsEmote("a is","This is a test"));
    }

}
