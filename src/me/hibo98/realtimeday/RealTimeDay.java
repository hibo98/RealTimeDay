package me.hibo98.realtimeday;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

public class RealTimeDay extends JavaPlugin {

    private static RealTimeDay instance;

    @Override
    public void onEnable() {
        RealTimeDay.instance = this;
        File configFile = new File("plugins/RealTimeDay/", "config.yml");
        Setup.setupConfig();
        if (!configFile.exists()) {
            try {
                this.getConfig().save(configFile);
            } catch (IOException ex) {
                RealTimeDay.error(ex);
            }
            RealTimeDay.warning("Please Configure the RealTimeDay Plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        try {
            this.getConfig().load(configFile);
        } catch (IOException | InvalidConfigurationException ex) {
            RealTimeDay.error(ex);
        }
        if (!this.getConfig().getBoolean("real-time-day")) {
            RealTimeDay.warning("Please Configure the RealTimeDay Plugin!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Time.setup(this.getConfig());
    }

    @Override
    public void onDisable() {
        Time.stop();
    }

    public static void info(Object msg) {
        instance.getLogger().log(Level.INFO, "{0}", msg);
    }

    public static void fine(Object msg) {
        instance.getLogger().log(Level.FINE, "{0}", msg);
    }

    public static void warning(Object msg) {
        instance.getLogger().log(Level.WARNING, "{0}", msg);
    }

    public static void error(Object msg) {
        instance.getLogger().log(Level.SEVERE, "{0}", msg);
    }

    public static RealTimeDay getInstance() {
        return RealTimeDay.instance;
    }

}
