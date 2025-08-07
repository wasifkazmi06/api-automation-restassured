package Xpress;

import dbUtils.Ocr_DBAccessObject;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.sql.SQLException;
import java.util.ArrayList;

import static util.ReturnRandomTxnId.returnRandomNumber;

@Slf4j
public class OcrService extends XpressData {
    public OcrService() throws Exception {
    }

    public static void updateOcrData(String approvalMethod, String mobile) throws SQLException, ClassNotFoundException {

        try {
            //Delete the existing entry for mobile from ocr db
            Ocr_DBAccessObject.deleteOnPostgreSqlDb(DELETE_MOBILE_ENTRY_OCR_DB.replace("$", mobile));

            // create query
            Ocr_DBAccessObject.createQuery(INSERT_PG_SCORE_OCR_DB, preparePgscoreRowData(approvalMethod, mobile));

        } catch (Exception e) {
            Assert.fail("Exception coming while updating pg affluence score in ocr DB" + e.getMessage());
        }

    }

    public static ArrayList<Object> preparePgscoreRowData(String approvalMethod, String mobile) throws SQLException, ClassNotFoundException {
        Integer newIdvalue = fetchLatestId();
        if (newIdvalue == null) {
            Assert.fail("Error while fetching latest id from ocr db.");
        }
        ArrayList<Object> rowValues = new ArrayList<>();
        rowValues.add(newIdvalue);
        rowValues.add(mobile);
        rowValues.add(returnRandomNumber());
        rowValues.add(DAYS_ON_PG);
        rowValues.add(MAX_SALE_AMOUNT_6M);
        rowValues.add(ECOMMERCE_SALE_3M);
        rowValues.add(addDaysInDate(-1));
        rowValues.add(addDaysInDate(30));

        switch (approvalMethod) {
            case "BNPL_XSELL":
            case "BUREAU_SURROGATE":
            case "BNPL_STPL":
            case "BNPL_XSELL_ONE_REPAYMENT":
                rowValues.add(3, PG_AFFLUENCE_SCORE); // Adding at the correct index
                break;
            case "STATED_INCOME":
                rowValues.add(3, SI_PG_AFFLUENCE_SCORE); // Adding at the correct index
                break;
            default:
                log.error("No valid approval method found");
                rowValues.clear(); // Clear the list if no valid approval method
                break;
        }
        return rowValues;
    }
}
