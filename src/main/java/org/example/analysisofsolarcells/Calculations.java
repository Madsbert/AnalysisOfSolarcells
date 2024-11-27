package org.example.analysisofsolarcells;
public class Calculations {

    //Method to Calculate Total Kwh
    public static int calculateTotalKwh(Measurement[]measurements) {
        int totalKwh = 0;
        for (int i =0; i<measurements.length;i++) {
            totalKwh += measurements[i].getOnline();
        }
        return totalKwh;
    }
}
