package me.hibo98.realtimeday;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public class Setup {

    public static void setupConfig() {
        FileConfiguration c = RealTimeDay.getInstance().getConfig();
        c.options().header("Config File of RealTimeDay:");
        List<String> defaultworld = new ArrayList();
        defaultworld.add("world");
        c.addDefault("real-time-day", false);
        c.addDefault("rtd-all-worlds", false);
        c.addDefault("rtd-worlds", defaultworld);
        c.addDefault("sync-intervall", 10);
        c.options().copyDefaults(true);

    }
}
