package me.hibo98.realtimeday;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                Logger.getLogger(RealTimeDay.class.getName()).log(Level.SEVERE, "Error while saving config for the first time", ex);
            }
            return;
        }
        try {
            getConfig().load(configFile);
            getConfig().save(configFile);
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(RealTimeDay.class.getName()).log(Level.SEVERE, "Error while loading config", ex);
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

    public static RealTimeDay getInstance() {
        return instance;
    }
    
    public static FileConfiguration getCfg() {
        return instance.getConfig();
    }
}
