package de.tkunkel.twitch.monitor;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.google.gson.Gson;
import de.tkunkel.twitch.monitor.types.config.Config;
import de.tkunkel.twitch.monitor.types.config.ConfigChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@SpringBootApplication
public class Starter {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Starter.class.getName());

        Gson gson = new Gson();
        InputStream configSource = Starter.class.getClassLoader().getResourceAsStream("private_config.json");
        if (Objects.isNull(configSource)) {
            throw new RuntimeException("private_config.json not found!");
        }
        Config config = gson.fromJson(new InputStreamReader(configSource), Config.class);
//         chat credential
        String accessToken = config.accessToken;
        OAuth2Credential credential = new OAuth2Credential("twitch", accessToken);

        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withEnableChat(true)
                .withEnableChat(true)
                .withChatAccount(credential)
                .build();

        EventManager eventManager = twitchClient.getEventManager();

        for (ConfigChannel channel : config.channels) {
            logger.info("Connecting to channel '"+channel.name+"'");
            twitchClient.getChat().joinChannel(channel.name);
        }

        eventManager.onEvent(ChannelMessageEvent.class, event -> {
            logger.info("[" + event.getChannel().getName() + "] " + event.getUser().getName() + ": " + event.getMessage());
        });
    }
}
