package me.hibo98.realtimeday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

public class Time {

    private static List<World> rtdWorlds = new ArrayList<>();
    private static BukkitTask scheduler;

    public static void setup() {
        if (RealTimeDay.getCfg().getBoolean("rtd-all-worlds")) {
            rtdWorlds = Bukkit.getWorlds();
        } else {
            RealTimeDay.getCfg().getStringList("rtd-worlds").stream().map((world) -> Bukkit.getWorld(world)).filter((w) -> (w != null)).forEach((w) -> {
                rtdWorlds.add(w);
            });
        }
        if (rtdWorlds.isEmpty()) {
            RealTimeDay.error("RealTimeDay is configured for 0 worlds");
            Bukkit.getPluginManager().disablePlugin(RealTimeDay.getInstance());
        }
        setupScheduler();
    }

    private static void setWorldsTime(Integer ticks) {
        rtdWorlds.stream().forEach((world) -> {
            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(ticks);
        });
    }

    private static void setupScheduler() {
        scheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(RealTimeDay.getInstance(), () -> {
            setWorldsTime(convertTime(getSystemTime()));
        }, 0, (20 * 60 * RealTimeDay.getCfg().getInt("sync-intervall")));
    }

    public static Calendar getSystemTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return calendar;
    }

    private static int convertTime(Calendar c) {
        RealTimeData rtd = RealTimeData.getByMonth(c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        if (rtd == null || (c.get(Calendar.HOUR_OF_DAY) == rtd.getAufgang() && c.get(Calendar.MINUTE) == 0)) {
            return 0;
        } else if (c.get(Calendar.HOUR_OF_DAY) > rtd.getAufgang() && c.get(Calendar.HOUR_OF_DAY) < rtd.getUntergang()) {
            return (int) (((c.get(Calendar.HOUR_OF_DAY) - rtd.getAufgang()) * 60 + c.get(Calendar.MINUTE)) * rtd.getTPMDay());
        } else if (c.get(Calendar.HOUR_OF_DAY) == rtd.getUntergang() && c.get(Calendar.MINUTE) == 0) {
            return 12000;
        } else {
            return (int) (((c.get(Calendar.HOUR_OF_DAY) - rtd.getAufgang()) * 60 + c.get(Calendar.MINUTE)) * rtd.getTPMNight());
        }
    }

    public static void stop() {
        if (scheduler != null) {
            scheduler.cancel();
        }
        rtdWorlds.stream().forEach((world) -> {
            world.setGameRuleValue("doDaylightCycle", "true");
        });
    }
}
