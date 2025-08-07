package util;

import java.io.*;
import java.util.Scanner;


public class SplitCSV {
   static String inputfile= "/Users/wasif_kazmi/Documents/DryRunCSVs/250K_DressRehearsal/05062023/PlannedForProd.csv";

    public static void splitCSV(int lines, int files) throws FileNotFoundException, IOException{

        BufferedReader br = new BufferedReader(new FileReader(inputfile)); //reader for input file intitialized only once
        String strLine = null;
        for (int i=1;i<=files;i++) {
                FileWriter fstream1 = new FileWriter("FileNumber_"+i+".csv"); //creating a new file writer.
            BufferedWriter out = new BufferedWriter(fstream1);
            for(int j=0;j<lines;j++){   //iterating the reader to read only the first few lines of the csv as defined earlier
                strLine = br.readLine();
                if (strLine!= null) {
                    String strar[] = strLine.split(" ");
                    out.write(strar[0]);   //acquring the first column
                    out.newLine();
                }
            }
            out.close();
        }
    }

    public static void main(String args[])
    {
        try{
            int lines = 30000;  //set this to whatever number of lines you need in each file
            int count = 0;
            File file = new File(inputfile);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {  //counting the lines in the input file
                scanner.nextLine();
                count++;
            }
            System.out.println(count);
            int files=0;
            if((count%lines)==0){
                files= (count/lines);
            }
            else{
                files=(count/lines)+1;
            }
            System.out.println(files); //number of files that shall eb created

            splitCSV(lines,files);
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}