package de.tkunkel.twitch.monitor;

import de.tkunkel.twitch.monitor.types.config.Config;
import de.tkunkel.twitch.monitor.types.config.ConfigChannel;
import de.tkunkel.twitch.monitor.types.config.ConfigEmote;

public class MessageProcessor {
    private Config config;
    private EmoteDetector emoteDetector = new EmoteDetector();

    public MessageProcessor(Config config) {
        this.config = config;
    }

    public void process(String channelName, String message) {
        for (ConfigChannel channel : config.channels) {
            if (channel.name.equalsIgnoreCase(channelName)) {
                for (ConfigEmote emote : channel.emotes) {
                    checkEmote(emote, message);
                }
            }
        }

    }

    private void checkEmote(ConfigEmote emote, String message) {
//        logger.info("emoteDetector.containsEmote("+emote.name+","+ message+")");
        if (emoteDetector.containsEmote(emote.name, message)) {
//            logger.info("+ " + message);
        }
    }
}
