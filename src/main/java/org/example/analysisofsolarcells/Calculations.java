package org.example.analysisofsolarcells;

import static org.example.analysisofsolarcells.Read.onlineVar;

public class Calculations {

    public static int calculateTotalKwh() {
        int totalKwh = 0;
        for (int i =0; i<onlineVar.length;i++) {
            totalKwh += onlineVar[i];
        }
        return totalKwh;
    }
}
