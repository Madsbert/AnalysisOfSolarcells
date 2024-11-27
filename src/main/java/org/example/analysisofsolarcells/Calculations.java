package org.example.analysisofsolarcells;
public class Calculations {

    public static int calculateTotalKwh(Measurement[]measurements) {
        int totalKwh = 0;
        for (int i =0; i<measurements.length;i++) {
            totalKwh += measurements[i].getOnline();
        }
        return totalKwh;
    }
}
