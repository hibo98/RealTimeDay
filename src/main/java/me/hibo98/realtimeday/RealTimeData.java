package me.hibo98.realtimeday;

import java.util.ArrayList;

public enum RealTimeData {

    JANI(0, 1, 15, 7, 16, 22.22, 13.33),
    JANII(0, 16, 31, 7, 17, 20, 14.29),
    FEB(1, 1, 28, 7, 17, 20, 14.29),
    MRZ(2, 1, 28, 6, 18, 16.66, 16.66),
    MRZSZ(2, 28, 31, 7, 19, 16.66, 16.66),
    APRI(3, 1, 15, 6, 19, 15.38, 18.18),
    APRII(3, 16, 30, 6, 20, 14.29, 20),
    MAI(4, 1, 31, 5, 20, 13.33, 22.22),
    JUN(5, 1, 30, 5, 21, 12.5, 25),
    JUL(6, 1, 31, 5, 21, 12.5, 25),
    AUGI(7, 1, 15, 5, 20, 13.33, 22.22),
    AUGII(7, 16, 31, 6, 20, 14.29, 20),
    SEPI(8, 1, 15, 6, 19, 15.38, 18.18),
    SEPII(8, 16, 30, 7, 19, 16.66, 16.66),
    OKT(9, 1, 23, 7, 18, 18.18, 15.38),
    OKTWZ(9, 24, 31, 6, 17, 18.18, 15.38),
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
        
    public static ArrayList<RealTimeData> getByMonth(int id) {
        ArrayList<RealTimeData> al = new ArrayList();
        for (RealTimeData rtd : values()) {
            if (rtd.month == id) al.add(rtd);
        }
        return al;
    }
}
