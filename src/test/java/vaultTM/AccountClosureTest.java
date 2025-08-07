package vaultTM;

import api.vaultTM.GetAccount;
import com.opencsv.CSVReader;
import constants.UtilConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojos.vaultTM.GetAccountPojo;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountClosureTest {

    static GetAccountPojo getAccountPojo = new GetAccountPojo();
    static GetAccount getAccount;

    static {
        try {
            getAccount = new GetAccount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String csvFile = "/Users/wasif_kazmi/Documents/DryRunCSVs/BulkAccountClosure/Prod_AccountClosed_05062023.csv";
    static String accountId = "LP-e0ed2ffbba6c47b6abe23af672fa7fa9";
    public AccountClosureTest() throws Exception {
    }

    @DataProvider(name = "testData")
    private static Object[][] readCsv() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(csvFile), ',');
        List<String[]> csvData = csvReader.readAll();
        Object[][] csvDataObject = new Object[csvData.size()][2];
        for (int i = 0; i < csvData.size(); i++) {
            csvDataObject[i] = csvData.get(i);
        }
        return csvDataObject;
    }

   /* @Test()
    public void validateAccountClosed() {*/
    @Test(dataProvider = "testData")
    public void validateAccountClosed(String accountId) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("x-api-key", UtilConstants.GRINGOTTS_XAPI_Key);

        getAccountPojo = getAccount.get(queryParamDetails, headerDetails, accountId);


        Assert.assertEquals(getAccountPojo.id, accountId, "Account ID mismatch!");
        Assert.assertEquals(getAccountPojo.product_id, VaultTMData.productId, "Product ID mismatch!");
        Assert.assertEquals(getAccountPojo.status, VaultTMData.expectedClosedAccountStatus, "Account  not closed, status mismatch!");

    }

}
