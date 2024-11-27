package org.example.analysisofsolarcells;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Read {

    //Array to save 24measurements (1day)
    static int onlineVar[] = new int[24];

    /**
     * Reads data from a TSV-file and saves the online measurements which matches a site-ID and date
     *
     * @param siteId - the site id which needs to be matched
     * @param datePickerDate - the date that needs to be matched
     * @throws FileNotFoundException - if the file isn't found
     */
    public static void readFile(int siteId, String datePickerDate) throws FileNotFoundException {

        //number of lines in the file
        int linesInFile = 100001;

        //Arrays to save the data from each column in the file
        int[] meassurementID = new int[linesInFile];
        String[] dates = new String[linesInFile];
        String[] time = new String[linesInFile];
        int[] site = new int[linesInFile];
        int[] total = new int[linesInFile];
        int[] online = new int[linesInFile];
        int[] offline = new int[linesInFile];

        //Tries to open the resource file from resource
        InputStream inputFile = Read.class.getResourceAsStream("/Udtræk af data fra solcelleanlæg.tsv");
        if (inputFile == null) {
            throw new FileNotFoundException("file not found");
        }

        Scanner scanner = new Scanner(inputFile);

        int index = 0;
        int matchFound = 0;
        int hoursInADay = 24;

        //goes through each line in the file until all 24 is found or file ends
        while (scanner.hasNextLine()&&matchFound<hoursInADay) {

            //reads a line and splits it into value based on tab or "T"
            String line = scanner.nextLine();
            String[] values = line.split("[\tT]");

            //saves the data from the line in these arrays
            meassurementID[index] = Integer.parseInt(values[0]);
            dates[index] = values[1];
            time[index] = values[2];
            site[index] = Integer.parseInt(values[3]);
            total[index] = Integer.parseInt(values[4]);
            online[index] = Integer.parseInt(values[5]);
            offline[index] = Integer.parseInt(values[6]);

            //controlls if the line matches the site ID and date
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

    /**
     * Returns a saved online measurement from det specific index
     * @param index -index for the wanted measurement (0-23)
     * @return Online production on the specific index
     */
    public int getOnlineVar(int index)
    {
        return onlineVar[index];
    }
}
