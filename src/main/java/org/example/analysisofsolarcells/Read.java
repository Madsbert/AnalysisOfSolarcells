package org.example.analysisofsolarcells;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Read {

    static int onlineVar[] = new int[24];

    public static void readFile(int siteId, String datePickerDate) throws FileNotFoundException {

        int linesInFile = 100001;
        int matchFound = 0;

        int[] meassurementID = new int[linesInFile];
        String[] dates = new String[linesInFile];
        String[] time = new String[linesInFile];
        int[] site = new int[linesInFile];
        int[] total = new int[linesInFile];
        int[] online = new int[linesInFile];
        int[] offline = new int[linesInFile];

        InputStream inputFile = Read.class.getResourceAsStream("/Udtræk af data fra solcelleanlæg.tsv");
        if (inputFile == null) {
            throw new FileNotFoundException("file not found");
        }
        Scanner scanner = new Scanner(inputFile);

        int index = 0;
        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();
            String[] values = line.split("[\tT]");

            meassurementID[index] = Integer.parseInt(values[0]);
            dates[index] = values[1];
            time[index] = values[2];
            site[index] = Integer.parseInt(values[3]);
            total[index] = Integer.parseInt(values[4]);
            online[index] = Integer.parseInt(values[5]);
            offline[index] = Integer.parseInt(values[6]);


            if(matchFound<24 && siteId == site[index] && datePickerDate.equals(dates[index]))
            {
                onlineVar[matchFound] = online[index];
                System.out.println(onlineVar[matchFound]);
                matchFound++;
            }

            index++;
        }

        scanner.close();

    }

    public int getOnlineVar(int index)
    {
        return onlineVar[index];
    }
}
