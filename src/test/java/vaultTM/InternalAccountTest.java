package vaultTM;

import api.vaultTM.InternalAccount;
import com.opencsv.CSVReader;
import constants.UtilConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojos.vaultTM.InternalAccountPojo;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternalAccountTest {

    InternalAccountPojo internalAccountPojo = new InternalAccountPojo();
    InternalAccount internalAccount = new InternalAccount();
    static String csvFile = "/Users/wasif_kazmi/Documents/DryRunCSVs/MigratedMerchant_Prod.csv";

    public InternalAccountTest() throws Exception {
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

    @Test(dataProvider = "testData")
    public void validateMerchantMigrated(String merchantId, String merchantName) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("X-Auth-Token", UtilConstants.VAULT_AUTH_TOKEN);

        queryParamDetails.put("page_size", "100");

        internalAccountPojo = internalAccount.get(queryParamDetails, headerDetails, merchantId);

        Assert.assertEquals(internalAccountPojo.id, merchantId, "Merchant id is not matching between lazypay and vault");
        Assert.assertEquals(internalAccountPojo.details.merchantName, merchantName, "Merchant name is not matching between lazypay and vault");
        Assert.assertEquals(internalAccountPojo.status, VaultTMData.expectedInternalAccountStatus, "The account status is not as expected!");

    }

}
