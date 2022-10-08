package de.tkunkel.twitch.monitor;

import org.apache.commons.lang.StringUtils;

public class EmoteDetector {

    public boolean containsEmote(String emote,String text) {
        if (StringUtils.isEmpty(emote) || StringUtils.isEmpty(text)) {
            return false;
        }
        return text.toLowerCase().contains(emote.toLowerCase());
    }
}
