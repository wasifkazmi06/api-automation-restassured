package util;

import com.opencsv.CSVReader;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.List;

public class ReadCSV {
    static String[] valuesToSend;

    public static String[] main(String csvFile) throws Exception {
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String row;
        try {
            while ((row = csvReader.readLine()) != null) {
                valuesToSend = row.split(",");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        csvReader.close();
        return valuesToSend;
    }

}
