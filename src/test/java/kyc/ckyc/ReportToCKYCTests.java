package kyc.ckyc;

import api.kyc.apis.ReportToCkyc;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.DBValidations;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

@Slf4j
public class ReportToCKYCTests extends BaseTestData {

    public ReportToCKYCTests() throws Exception {
    }

    ReportToCkyc reportToCkyc = new ReportToCkyc();
    DBValidations dbData = new DBValidations();

    @Description("Creates report of the users who's KYC done via axml")
    @Feature("REPORT_TO_CKYC")
    @Test(priority = 1,dataProvider = "ckycReportingTestdata")
    public void reportToCkycValidations(String testcase, String uuid, String product) {
        HashMap<String, String> pathParam = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        pathParam.put("uuid", uuid);
        pathParam.put("product", product);
        String status = reportToCkyc.postWithoutResponseBody(pathParam,headerDetails);
        log.info("response statuscode is : " +status+ " for test : "+testcase);
        Assert.assertEquals(status,"200","Received incorrect responsecode");
        dbData.validateCKYCReportEntry(uuid,0);

    }


}
