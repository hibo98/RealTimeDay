package me.hibo98.realtimeday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class Time {
    
    private static List<World> rtdWorlds = new ArrayList();
    private static int scheduler = 0;
    private static boolean started = false;
    
    public static void setup() {
        if (RealTimeDay.config.getBoolean("rtd-all-worlds")) {
            setupWorlds(Bukkit.getWorlds());
            rtdWorlds = Bukkit.getWorlds();
        } else {
            for (String world : RealTimeDay.config.getStringList("rtd-worlds")) {
                World w = Bukkit.getWorld(world);
                if (w == null) continue;
                rtdWorlds.add(w);
            }
        }
        setupWorlds(rtdWorlds);
        setupScheduler();
    }
    
    private static void setupWorlds(List<World> worlds) {
        for (World world : worlds) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(0);
        }
    }
    
    private static void setWorldsTime(Integer ticks) {
        for (World world : rtdWorlds) {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(ticks);
        }
    }
    
    private static void setupScheduler() {
        scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(RealTimeDay.getInstance(), new Runnable() {
            @Override
            public void run() {
                started = true;
                Integer[] time = getSystemTime();
                int TTime = convertTime(time[0], time[1]);
                setWorldsTime(TTime);
            }
        }, 0, (20 * 60 * RealTimeDay.config.getInt("sync-intervall")));
    }
    
    private static Integer[] getSystemTime() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Integer[] time = new Integer[]{hour, minute};
        return time;
    }
    
    private static int convertTime(Integer hour, Integer minute) {
        int TMinute = (int) (83 * (round(minute, 5)) / 5);
        int THour;
        if (hour >= 6) {
            THour = (hour - 6) * 1000;
        } else {
            THour = ((hour + 24) - 6) * 1000;
        }
        return THour + TMinute;
    }
    
    private static double round(double num, int multipleOf) {
        return Math.floor((num + (double) multipleOf / 2) / multipleOf) * multipleOf;
    }
    
    public static void stop() {
        if (started) {
            Bukkit.getScheduler().cancelTask(scheduler);
            for (World world : rtdWorlds) {
                world.setGameRuleValue("doDaylightCycle", "true");
            }
        }
    }
}
