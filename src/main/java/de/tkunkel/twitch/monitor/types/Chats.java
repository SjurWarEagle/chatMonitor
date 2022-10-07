package de.tkunkel.twitch.monitor.types;

import java.util.HashMap;
import java.util.Map;

public class Chats {

    public Map<String, EmotesPerInterval> chat2emotesPerInterval = new HashMap<>();

    public Integer getNumberOfEmotesInInterval(String chat,String emote){
        if (!chat2emotesPerInterval.containsKey(chat)){
            return 0;
        }
        return chat2emotesPerInterval.get(chat).getNumberOfEmotesInInterval(emote);
    }

    public void rememberEmote(String chat,String emote){
        if (!chat2emotesPerInterval.containsKey(chat)){
            return;
        }
        chat2emotesPerInterval.get(chat).rememberEmote(emote);
    };
}
