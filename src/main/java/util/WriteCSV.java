package util;

import java.io.*;

public class WriteCSV {

    public static void  main(String[] args) {

        try{
            FileWriter fstream = new FileWriter(args[2],true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.append("\n");
            out.append(args[0]);
            out.append(',');
            out.append(args[1]);
            out.close();
        }catch (Exception e){
            System.err.println("Error while writing to file: " +
                    e.getMessage());
        }
    }
}
