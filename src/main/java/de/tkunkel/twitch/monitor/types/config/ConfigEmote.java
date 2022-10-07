package de.tkunkel.twitch.monitor.types.config;

public class ConfigEmote {
    public String name;
    public int cnt;
    public int intervalInSeconds;
    public int coolDownInSeconds;

    @Override
    public String toString() {
        return "ConfigEmote{" +
                "name='" + name + '\'' +
                ", cnt=" + cnt +
                ", intervalInSeconds=" + intervalInSeconds +
                ", coolDownInSeconds=" + coolDownInSeconds +
                '}';
    }
}
