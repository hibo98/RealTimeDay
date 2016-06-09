package me.hibo98.realtimeday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

public class Time {

    private static List<World> rtdWorlds = new ArrayList();
    private static BukkitTask scheduler;

    public static void setup() {
        if (RealTimeDay.getCfg().getBoolean("rtd-all-worlds")) {
            rtdWorlds = Bukkit.getWorlds();
        } else {
            for (String world : RealTimeDay.getCfg().getStringList("rtd-worlds")) {
                World w = Bukkit.getWorld(world);
                if (w == null) {
                    continue;
                }
                rtdWorlds.add(w);
            }
        }
        setupWorlds();
        setupScheduler();
    }

    private static void setupWorlds() {
        for (World world : rtdWorlds) {
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
        scheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(RealTimeDay.getInstance(), new Runnable() {
            @Override
            public void run() {
                Integer[] time = getSystemTime();
                setWorldsTime(convertTime(time));
            }
        }, 0, (20 * 60 * RealTimeDay.getCfg().getInt("sync-intervall")));
    }

    private static Integer[] getSystemTime() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        Integer[] time = new Integer[]{calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)};
        return time;
    }

    private static int convertTime(Integer[] data) {
        ArrayList<RealTimeData> al = RealTimeData.getByMonth(data[2]);
        for (RealTimeData rtd : al) {
            if (rtd.getBeginDate() <= data[3] && data[3] <= rtd.getEndDate()) {
                if (data[0] == rtd.getAufgang() && data[1] == 0) {
                    return 0;
                } else if (data[0] > rtd.getAufgang() && data[0] < rtd.getUntergang()) {
                    return (int) (((data[0] - rtd.getAufgang()) * 60 + data[1]) * rtd.getTPMDay());
                } else if (data[0] == rtd.getUntergang() && data[1] == 0) {
                    return 12000;
                } else {
                    return (int) (((data[0] - rtd.getAufgang()) * 60 + data[1]) * rtd.getTPMNight());
                }
            }
        }
        return 0;
    }

    public static void stop() {
        if (scheduler != null) {
            scheduler.cancel();
        }
        for (World world : rtdWorlds) {
            world.setGameRuleValue("doDaylightCycle", "true");
        }
    }
}
