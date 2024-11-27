package org.example.analysisofsolarcells;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Read {

    static int onlineVar[] = new int[24];
    static int onlineVarMonth[] = new int[31];

    public static void readFile(int siteId, String datePickerDate) throws FileNotFoundException {

        int linesInFile = 100001;

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
        int matchFound = 0;

        while (scanner.hasNextLine()&&matchFound<24) {


            String line = scanner.nextLine();
            String[] values = line.split("[\tT]");

            meassurementID[index] = Integer.parseInt(values[0]);
            dates[index] = values[1];
            time[index] = values[2];
            site[index] = Integer.parseInt(values[3]);
            total[index] = Integer.parseInt(values[4]);
            online[index] = Integer.parseInt(values[5]);
            offline[index] = Integer.parseInt(values[6]);


            if(siteId == site[index] && datePickerDate.equals(dates[index]))
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

    public static void fileReaderMonthly(int siteId, int selectedMonth, int selectedYear) throws FileNotFoundException {
        int linesInFile = 100001;

        int[] meassurementID = new int[linesInFile];
        int[] year = new int[linesInFile];
        int[] month = new int[linesInFile];
        int[] day = new int[linesInFile];
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

        int indexMonth = 0;
        int matchFoundMonth = 0;

        while (scanner.hasNextLine() && matchFoundMonth<24) {

            String line = scanner.nextLine();
            String[] values = line.split("[\tT-]");

            meassurementID[indexMonth] = Integer.parseInt(values[0]);
            year[indexMonth] = Integer.parseInt(values[1]);
            month[indexMonth] = Integer.parseInt(values[2]);
            day[indexMonth] = Integer.parseInt(values[3]);
            time[indexMonth] = values[4];
            site[indexMonth] = Integer.parseInt(values[5]);
            total[indexMonth] = Integer.parseInt(values[6]);
            online[indexMonth] = Integer.parseInt(values[7]);
            offline[indexMonth] = Integer.parseInt(values[8]);


            if(siteId == site[indexMonth] && selectedMonth==(month[indexMonth]) && selectedYear == year[indexMonth])
            {
                onlineVar[matchFoundMonth] = online[indexMonth];
                System.out.println(onlineVar[matchFoundMonth]);
                matchFoundMonth++;
            }

            indexMonth++;
        }

        scanner.close();

    }
}

