package de.tkunkel.twitch.monitor.web;

import de.tkunkel.twitch.monitor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("trigger")
public class TriggerController {

    @Autowired
    private MessageProcessor messageProcessor;
    private final Logger logger = LoggerFactory.getLogger(TriggerController.class.getName());

    @GetMapping(value = "/{channel}/{emote}")
    public EventTriggeredResponse getTriggerAndSetToRead(@PathVariable("channel") String channel, @PathVariable("emote") String emote) {
        logger.info("TriggerController called");
        EventTriggeredResponse response = new EventTriggeredResponse();
        messageProcessor.getTriggerAndSetToRead(channel,emote);
        return response;
    }
}
