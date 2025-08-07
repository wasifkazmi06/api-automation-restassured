package kyc.digilocker;

import api.kyc.digilocker.GetDigilockerStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.digilocker.StatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class StatusTests extends BaseTestData {

    public StatusTests() throws Exception {
    }
    GetDigilockerStatus statusdetails = new GetDigilockerStatus();
    boolean resultprocessed;

    @Description("Verify the digilocker status along with each document status")
    @Feature("DGILOCKER_RESULTS")
    @Test(priority = 1,dataProvider = "digilockerStatusTestData")
    public void digilockerStatusValidations(String testname, String apiKey, String source, String principalid,String txnId) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        headerDetails.put("source", source);
        headerDetails.put("x-api-key", apiKey);
        queryParamDetails.put("principalId", principalid);
        queryParamDetails.put("transactionId", txnId);

        StatusResponsePojo statusResponse = statusdetails.get(queryParamDetails, headerDetails);
        switch (testname) {
            case "WITH_VALID_INPUTS":
            case "VALID_SOURCE_PS":
            case "VALID_SOURCE_SMB":
                Assert.assertEquals(statusResponse.getStatus(),"SUCCESS", "getting incorrect status");
                Assert.assertNotNull(statusResponse.getData(), "failed to process the results");
                Assert.assertNotNull(statusResponse.getData().get(0).getStatus(), "failed to process the results");
                break;
            case "WITH_APPROVED_TRANSACTIONID":
                resultprocessed = statusResponse.getData().get(0).getMetadata().isResultsProcessed();
                Assert.assertEquals(statusResponse.getStatus(),"SUCCESS", "getting incorrect status");
                Assert.assertNotNull(statusResponse.getData().get(0).getMetadata(), "failed to process the results");
             //   Assert.assertEquals(resultprocessed,true,"resultprocessed flag is incorrect");
                break;
            case "WITH_DECLINED_TRANSACTIONID":
                resultprocessed = statusResponse.getData().get(0).getMetadata().isResultsProcessed();
                Assert.assertEquals(statusResponse.getStatus(),"SUCCESS", "getting incorrect status");
                Assert.assertNotNull(statusResponse.getData().get(0).getMetadata(), "failed to process the results");
             //   Assert.assertEquals(resultprocessed,false,"resultprocessed flag is incorrect");
                break;
            case "INVALID_APIKEY":
            case "INVALID_SOURCE":
                Assert.assertEquals(statusResponse.getErrorData().getErrorCode(), "INVALID_API_KEY", "getting incorrect error code");
                Assert.assertEquals(statusResponse.getErrorData().getErrorMsg(), "Unauthorised access.", "getting incorrect error message");
                break;
            case "INVALID_TRANSACTIONID":
            case "NONEXIST_TRANSACTIONID":
                Assert.assertEquals(statusResponse.getErrorData().getErrorCode(), "TRANSACTION_ID_NOT_FOUND", "getting incorrect error code");
                Assert.assertEquals(statusResponse.getErrorData().getErrorMsg(), "Transaction Id not found in the db");
                break;
            default:
                Assert.assertEquals(statusResponse.getError(),"INTERNAL_SERVER_ERROR","getting incorrect error code");
        }
    }
    @Description("Verify the digilocker status along with each document status")
    @Feature("DGILOCKER_RESULTS")
    @Test(priority = 2,dataProvider = "NonRequiredParamTestData")
    public void statusNonRequiredParamsValidations(String testname, String apiKey, String source, String param) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        headerDetails.put("source", source);
        headerDetails.put("x-api-key", apiKey);
        if(testname.contains("PRINCIPALID")) {
            queryParamDetails.put("principalId", param);
        }else{
                queryParamDetails.put("transactionId", param);
        }

        StatusResponsePojo statusResponse = statusdetails.get(queryParamDetails, headerDetails);
        switch (testname) {
            case "WITH_ONLY_PRINCIPALID":
            case "WITH_ONLY_TRANSACTIONID":
                Assert.assertEquals(statusResponse.getStatus(),"SUCCESS", "getting incorrect status");
                Assert.assertNotNull(statusResponse.getData(), "failed to process the results");
                Assert.assertNotNull(statusResponse.getData().get(0).getStatus(), "failed to process the results");
                break;
            case "EMPTY_TRANSACTIONID":
            case "EMPTY_PRINCIPALID":
                Assert.assertEquals(statusResponse.getErrorData().getErrorCode(), "INVALID_REQUEST_PARAMS", "getting incorrect error code");
                Assert.assertEquals(statusResponse.getErrorData().getErrorMsg(), "Either provide principalId or transactionId");
                break;
            case "NONEXIST_PRINCIPALID":
                Assert.assertEquals(statusResponse.getErrorData().getErrorCode(), "LINK_NOT_FOUND", "getting incorrect error code");
                Assert.assertEquals(statusResponse.getErrorData().getErrorMsg(), "Start Link not found","Incorrect error message");
                break;
        }
    }
}
