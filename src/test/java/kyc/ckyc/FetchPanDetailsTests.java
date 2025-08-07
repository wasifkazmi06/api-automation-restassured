package kyc.ckyc;

import api.kyc.apis.FetchPanDetails;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import kyc.BaseTestData;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.apis.KYCStatusPojo;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FetchPanDetailsTests extends BaseTestData {

    public FetchPanDetailsTests() throws Exception {
    }
    FetchPanDetails panDetails = new FetchPanDetails();

    @Description("Fetch the uploaded PAN details of an user")
    @Feature("PAN_DETAILS")
    @Test(priority = 1,dataProvider = "panDetailsTestdata")
    public void panDetailValidations(String testcase, String uuid) {
        Map<String, Object> queryParam = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        queryParam.put("uuid", uuid);
        Response response = panDetails.getWithResponse(queryParam, headerDetails);
        KYCStatusPojo kycStatusPojo = response.getBody().as(KYCStatusPojo.class);
        switch (testcase) {
            case "ValidUuid":
                Assert.assertEquals(response.getStatusCode(), 200, "received failed status code");
                break;
            case "IncorrectUuid":
            case "WithNullUuid":
                Assert.assertEquals(kycStatusPojo.getStatus(), "400", "Received incorrect responsecode");
                Assert.assertEquals(kycStatusPojo.getMessage(), "Empty/Invalid uuid in the request", "error message mismatched");
                break;
            case "UuidWithNoPanUploaded":
                Assert.assertEquals(kycStatusPojo.getStatus(), "404", "Received incorrect responsecode");
                Assert.assertEquals(kycStatusPojo.getMessage(), "Verified Pan details not found", "error message mismatched");
                break;
        }
    }


}
