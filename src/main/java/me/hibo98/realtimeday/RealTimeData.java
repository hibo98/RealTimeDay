package me.hibo98.realtimeday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public enum RealTimeData {

    JANI(0, 1, 15, 7, 16, 22.22, 13.33),
    JANII(0, 16, 31, 7, 17, 20, 14.29),
    FEB(1, 1, 29, 7, 17, 20, 14.29),
    MRZ(2, 1, lastSundayOf(3) - 1, 6, 18, 16.66, 16.66),
    MRZSZ(2, lastSundayOf(3), 31, 7, 19, 16.66, 16.66),
    APRI(3, 1, 15, 6, 19, 15.38, 18.18),
    APRII(3, 16, 30, 6, 20, 14.29, 20),
    MAI(4, 1, 31, 5, 20, 13.33, 22.22),
    JUN(5, 1, 30, 5, 21, 12.5, 25),
    JUL(6, 1, 31, 5, 21, 12.5, 25),
    AUGI(7, 1, 15, 5, 20, 13.33, 22.22),
    AUGII(7, 16, 31, 6, 20, 14.29, 20),
    SEPI(8, 1, 15, 6, 19, 15.38, 18.18),
    SEPII(8, 16, 30, 7, 19, 16.66, 16.66),
    OKT(9, 1, lastSundayOf(10) - 1, 7, 18, 18.18, 15.38),
    OKTWZ(9, lastSundayOf(10), 31, 6, 17, 18.18, 15.38),
    NOV(10, 1, 30, 7, 16, 22.22, 13.33),
    DEZ(11, 1, 31, 7, 16, 22.22, 13.33);

    private RealTimeData(int month, int beginDate, int endDate, int aufgang, int untergang, double tpmD, double tpmN) {
        this.aufgang = aufgang;
        this.untergang = untergang;
        this.tpmD = tpmD;
        this.tpmN = tpmN;
        this.month = month;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    private final int aufgang;
    private final int untergang;
    private final double tpmD;
    private final double tpmN;
    private final int month;
    private final int beginDate;
    private final int endDate;

    public int getAufgang() {
        return aufgang;
    }

    public int getUntergang() {
        return untergang;
    }

    public double getTPMDay() {
        return tpmD;
    }

    public double getTPMNight() {
        return tpmN;
    }

    public int getBeginDate() {
        return beginDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public static RealTimeData getByMonth(int month, int day) {
        for (RealTimeData rtd : values()) {
            if (rtd.month == month && rtd.getBeginDate() <= day && day <= rtd.getEndDate()) {
                return rtd;
            }
        }
        return null;
    }

    public static int lastSundayOf(int month) {
        return LocalDate.of(Time.getSystemTime().get(Calendar.YEAR), month, 1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .getDayOfMonth();
    }
}
