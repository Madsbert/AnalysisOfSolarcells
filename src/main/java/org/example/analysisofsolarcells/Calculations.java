package org.example.analysisofsolarcells;
public class Calculations {

    //Method to Calculate Total Kwh
    public static int calculateTotalKwh(Measurement[]measurements) {
        int totalKwh = 0;
        for (int i =0; i<measurements.length;i++) {
            if(measurements[i]==null)
            {
                System.out.println("No such measurement");;
            }
            else
            {
                totalKwh += measurements[i].getOnline();
            }
        }
        return totalKwh;
    }
}
