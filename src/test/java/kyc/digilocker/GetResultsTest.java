package kyc.digilocker;

import api.kyc.digilocker.GetResults;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.kyc.digilocker.ResultsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class GetResultsTest extends BaseTestData {
    public GetResultsTest() throws Exception {
    }

    GetResults results = new GetResults();

    @Description("Verify the results processed post the digilocker journey completed with approved/declined states")
    @Feature("DGILOCKER_RESULTS")
    @Test(priority = 1,dataProvider = "digilockerResultsTestData")
    public void digilockerResultsValidations(String testname, String apiKey, String source, String txnId) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "*/*");
        headerDetails.put("source", source);
        headerDetails.put("x-api-key", apiKey);
        queryParamDetails.put("transactionId",txnId);
        ResultsResponsePojo resultsResponse = results.get(queryParamDetails,headerDetails);
        switch(testname) {
            case "WITH_VALID_INPUTS":
            case "VALID_SOURCE_LP":
            case "VALID_SOURCE_PS":
            case "VALID_SOURCE_SMB":
            case "AFTER_DIGILOCKER_JOURNEY_APPROVED":
                Assert.assertEquals(resultsResponse.getStatus(),"SUCCESS", "getting incorrect status");
                Assert.assertNotNull(resultsResponse.getTransactionId(), "failed to process the results");
                Assert.assertNotNull(resultsResponse.getData(), "failed to process the results");
                break;
            case "INVALID_APIKEY":
            case "INVALID_SOURCE":
                Assert.assertEquals(resultsResponse.getErrorData().getErrorCode(), "INVALID_API_KEY", "getting incorrect error code");
                Assert.assertEquals(resultsResponse.getErrorData().getErrorMsg(), "Unauthorised access.", "getting incorrect error message");
                break;
            case "INVALID_TRANSACTIONID":
            case "NONEXIST_TRANSACTIONID":
                Assert.assertEquals(resultsResponse.getErrorData().getErrorCode(), "TRANSACTION_ID_NOT_FOUND", "Validations failed");
                Assert.assertEquals(resultsResponse.getErrorData().getErrorMsg(), "Transaction Id not found in the db","getting incorrect error message");
                break;
            case "EMPTY_TRANSACTIONID":
                Assert.assertEquals(resultsResponse.getErrorData().getErrorCode(), "INVALID_TRANSACTION_ID", "Validations failed");
                Assert.assertEquals(resultsResponse.getErrorData().getErrorMsg(), "transaction id cannot be empty or null.","getting incorrect error message");
                break;
            case "AFTER_DIGILOCKER_JOURNEY_STARTED":
            case "AFTER_DIGILOCKER_JOURNEY_DECLINED":
                Assert.assertEquals(resultsResponse.getErrorData().getErrorCode(), "INAPPROPRIATE_RESULT_ENTRIES", "Validations failed");
                Assert.assertEquals(resultsResponse.getErrorData().getErrorMsg(), "Not appropriate result and result processing entries","getting incorrect error message");
                break;

        }


    }
}
