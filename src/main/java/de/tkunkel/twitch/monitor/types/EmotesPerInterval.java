package de.tkunkel.twitch.monitor.types;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EmotesPerInterval {
    private int cnt;
    private long duration;
    private TimeUnit unit;

    public Map<String, List<Long>> emotesPerInterval = new HashMap<>();

    public EmotesPerInterval(int cnt, TimeUnit unit) {
        this.cnt = cnt;
        this.unit = unit;
        duration = unit.toMillis(cnt);
    }

    public void rememberEmote(String emote) {
        emotesPerInterval.putIfAbsent(emote, new ArrayList<>());
        Long now = new Date().getTime();

        emotesPerInterval.get(emote).add(now);
        removeObsoleteEntries(emote);
    }

    public Integer getNumberOfEmotesInInterval(String emote) {
        return emotesPerInterval.getOrDefault(emote, new ArrayList<>()).size();
    }

    public void removeObsoleteEntries(String emote) {
        List<Long> existing = emotesPerInterval.getOrDefault(emote, new ArrayList<>());
        long now = new Date().getTime();
        List<Long> reduced = existing
                .stream()
                .filter(timestamp -> timestamp +duration>= now)
                .toList();
        if (reduced.size() == 0) {
            emotesPerInterval.remove(emote);
        } else {
            emotesPerInterval.put(emote, new ArrayList<>(reduced));
        }
    }
}
