package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PrepareExcel {

    public static void  createPhyNachFile(String firstCollectionDate, String finalCollectionDate, String maxAmt, String debtorOtherDetails,
                                         String status, String rtnCode, String reason) throws Exception {
        //Read the spreadsheet that needs to be updated

        FileInputStream file = new FileInputStream(new File("./File.xlsx"));


        //Access the workbook

        XSSFWorkbook wb = new XSSFWorkbook(file);
        XSSFSheet sheet = wb.getSheet("PENDING BATCH");
        XSSFRow row = null;
        XSSFCell cell = null;

            /*HSSFWorkbook wb = new HSSFWorkbook(fsIP);
            HSSFSheet sheet = wb.getSheet("PENDING BATCH");
            HSSFRow row = null;
            HSSFCell cell = null;*/

        for (int i = 0; i < sheet.getLastRowNum(); i++) {

            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell.getStringCellValue().equals("First Collection Date")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    cell.setCellValue(firstCollectionDate);
                    cell = sheet.getRow(i).getCell(j);
                }
                if (cell.getStringCellValue().equals("Final Collection Date")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    cell.setCellValue(finalCollectionDate);
                    cell = sheet.getRow(i).getCell(j);
                }
                if (cell.getStringCellValue().equals("Maximum Amount")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    cell.setCellValue(maxAmt);
                    cell = sheet.getRow(i).getCell(j);
                }
                if (cell.getStringCellValue().equals("Debtor other details")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    cell.setCellValue(debtorOtherDetails);
                    cell = sheet.getRow(i).getCell(j);
                }
                if (cell.getStringCellValue().equals("STATUS")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    //cell.setCellValue(status);
                    cell.setCellValue(status);
                    cell = sheet.getRow(i).getCell(j);
                }
                if (cell.getStringCellValue().equals("RTN CODE")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    //cell.setCellValue(rtnCode);
                    cell.setCellValue(rtnCode);
                    cell = sheet.getRow(i).getCell(j);
                }
                if (cell.getStringCellValue().equals("REASON")) {
                    cell = sheet.getRow(i+1).getCell(j);
                    //cell.setCellValue(reason);
                    cell.setCellValue(reason);
                    cell = sheet.getRow(i).getCell(j);
                }
            }
            file.close();
            //Open FileOutputStream to write updates
            FileOutputStream output_file = new FileOutputStream(new File("./File1.xlsx"));
            //Write changes
            wb.write(output_file);
            //Close the stream
            output_file.close();
        }
    }
}