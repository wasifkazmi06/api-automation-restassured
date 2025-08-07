package kyc.digilocker;

import api.kyc.digilocker.StartLink;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import kyc.BaseTestData;
import kyc.DBValidations;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojos.kyc.digilocker.StartLinkPojo;
import util.StringTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StartLinkTests extends BaseTestData {

        StartLink startRequest = new StartLink();
    DBValidations dbData = new DBValidations();
    public StartLinkTests() throws Exception {
        }
        @Description("Verifying the request sending to HV to get the startlink in response to start the digilocker journey")
        @Feature("DGILOCKER_RESULTS")
        @Test(dataProvider = "startLinkRequestDate")
        public void getStartLinkResponseWithValidInputs(String testname,String apiKey,String source,String principalid,String redirecturl) throws SQLException, ClassNotFoundException {

            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();
            headerDetails.put("cache-control", "no-cache");
            headerDetails.put("source", source);
            headerDetails.put("x-api-key", apiKey);
            headerDetails.put("Content-Type", "application/json");
            switch (testname) {
                case "WITH_TERMINAL_STATE_DECLINED_USER":
                    dbData.InsertDigiLockerAutoDeclinedUser();
                    break;
            }
            String reqPayload = new StringTemplate().replace("principalId", principalid).replace("redirectUrl", redirecturl).toString();
            System.out.println(reqPayload);
            StartLinkPojo startLinkResponse = startRequest.post(queryParamDetails, headerDetails, reqPayload);
            Assert.assertEquals(startLinkResponse.getStatus(),"SUCCESS","Invalid status");
            log.info("Digilocker Onboarding Status is : " + startLinkResponse.getStatus());
            Assert.assertNotNull(startLinkResponse.getData().getTransactionId());
            log.info("Digilocker transaction id is : " + startLinkResponse.getData().getTransactionId());
            Assert.assertNotNull(startLinkResponse.getData().getStartDigilockerUrl());
            log.info("Webhook URL received to start the digilocker journey is : " + startLinkResponse.getData().getStartDigilockerUrl());
            //DB Validations

            switch (testname) {
                case "WITH_TERMINAL_STATE_DECLINED_USER":
                    dbData.DeleteDigiLockerAutoDeclinedUser(principalid);
                    break;
                default:
                    Assert.assertEquals(dbData.validateStartLinkEntry(principalid),"STARTED","data is incorrect in db with incorrect status");

            }
        }

        @Description("Validating the startlink response with invalid inputs")
        @Feature("DGILOCKER_RESULTS")
        @Test(dataProvider = "startLinkInvalidPayloadData")
        public void startLinkResponseWithInvalidInputs(String testname,String apiKey,String source,String principalid,String redirecturl) {

            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();
            headerDetails.put("cache-control", "no-cache");
            headerDetails.put("source", source);
            headerDetails.put("x-api-key",apiKey);
            headerDetails.put("Content-Type","application/json");
            String reqPayload = new StringTemplate().replace("principalId",principalid).replace("redirectUrl",redirecturl).toString();
            StartLinkPojo startLinkResponse = startRequest.post(queryParamDetails, headerDetails, reqPayload);
            log.info("Digilocker Onboarding Status is : " + startLinkResponse.getStatus());
            Assert.assertEquals(startLinkResponse.getStatus(),"FAILED","Validations failed");

            switch(testname){
                case "INVALID_APIKEY":
                case "INVALID_SOURCE":
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorCode(),"INVALID_API_KEY","Validations failed");
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorMsg(),"Unauthorised access.");
                    break;
                case "EMPTY_PRINCIPALID":
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorCode(),"INVALID_PRINCIPAL_ID","Validations failed");
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorMsg(),"principal id cannot be empty or null.");
                    break;
                case "EMPTY_REDIRECTURL":
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorCode(),"INVALID_REDIRECT_URL","Validations failed");
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorMsg(),"redirect url cannot be empty or null.");
                    break;
                case "WITH_TERMINAL_STATE_USER":
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorCode(),"USER_IN_TERMINAL_STATE","Validations failed");
                    Assert.assertEquals(startLinkResponse.getErrorData().getErrorMsg(),"Digilocker is in terminal state for the user");
                    break;

            }
        }

}
