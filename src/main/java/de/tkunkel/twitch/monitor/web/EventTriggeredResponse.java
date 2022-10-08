package de.tkunkel.twitch.monitor.web;

public class EventTriggeredResponse {

    private boolean eventOccured;

    public boolean isEventOccured() {
        return eventOccured;
    }

    public void setEventOccured(boolean eventOccured) {
        this.eventOccured = eventOccured;
    }
}
