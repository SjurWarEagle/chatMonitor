package de.tkunkel.twitch.monitor.types;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class EmotesPerIntervalTrigger {
    private final int triggerCnt;
    private final long duration;
    private final long durationCoolDown;

    public Map<String, Long> emotesEventsFiredAt = new HashMap<>();
    public Map<String, Boolean> emotesEventsRead = new HashMap<>();
    public Map<String, List<Long>> emotesPerInterval = new HashMap<>();

    public EmotesPerIntervalTrigger(int triggerCnt, int cnt, TimeUnit unit, int cntCooldown, TimeUnit unitCooldown) {
        this.triggerCnt = triggerCnt;
        durationCoolDown = unitCooldown.toMillis(cntCooldown);
        duration = unit.toMillis(cnt);
    }

    public boolean readEventFiredMarkerAndSetToReceived(String emote) {
        if (!emotesEventsRead.get(emote)) {
            emotesEventsRead.put(emote, true);
            return true;
        }
        return false;
    }

    public void rememberEmote(String emote) {
        long now = new Date().getTime();
        long coolDownUntil = emotesEventsFiredAt.getOrDefault(emote, 0L) + durationCoolDown;
//        System.out.println("coolDownUntil:"+coolDownUntil);
//        System.out.println("now:          "+now);
        if (coolDownUntil >= now) {
            //still cooling down
            return;
        }
        emotesPerInterval.putIfAbsent(emote, new ArrayList<>());

        emotesPerInterval.get(emote).add(now);
        removeObsoleteEntries(emote);
        if (triggerCnt <= getNumberOfEmotesInInterval(emote)) {
            triggerEvent(emote);
        }
    }

    private void triggerEvent(String emote) {
        long now = new Date().getTime();

        emotesEventsFiredAt.put(emote, now);
        emotesEventsRead.put(emote, false);
        emotesPerInterval.get(emote).clear();
    }

    protected Integer getNumberOfEmotesInInterval(String emote) {
        return emotesPerInterval.getOrDefault(emote, new ArrayList<>()).size();
    }

    protected void removeObsoleteEntries(String emote) {
        List<Long> existing = emotesPerInterval.getOrDefault(emote, new ArrayList<>());
        long now = new Date().getTime();
        List<Long> reduced = existing
                .stream()
                .filter(timestamp -> timestamp + duration >= now)
                .toList();
        if (reduced.size() == 0) {
            emotesPerInterval.remove(emote);
        } else {
            emotesPerInterval.put(emote, new ArrayList<>(reduced));
        }
    }
}
