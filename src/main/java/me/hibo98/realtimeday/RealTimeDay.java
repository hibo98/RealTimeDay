package me.hibo98.realtimeday;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RealTimeDay extends JavaPlugin {

    private static RealTimeDay instance;

    @Override
    public void onEnable() {
        instance = this;
        File configFile = new File(getDataFolder(), "config.yml");
        Setup.setupConfig();
        if (!configFile.exists()) {
            try {
                getConfig().save(configFile);
            } catch (IOException ex) {
                RealTimeDay.error(ex);
            }
            return;
        }
        try {
            getConfig().load(configFile);
            getConfig().save(configFile);
        } catch (IOException | InvalidConfigurationException ex) {
            RealTimeDay.error(ex);
        }
        if (!getConfig().getBoolean("real-time-day")) {
            Bukkit.getPluginManager().disablePlugin(instance);
            return;
        }
        Time.setup();
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
        return instance;
    }
    
    public static FileConfiguration getCfg() {
        return instance.getConfig();
    }
}
