package platform.userplatform;

import api.ams.clickOnboard;
import api.ams.clickOnboardNegative;
import io.qameta.allure.Description;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.PlatformSupportData;
import pojos.ams.ClicnkOnboardResponseNegative;
import pojos.ams.clinkOnboardResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class clinkOnboardTest extends UserPlatformSupportData {

    public clinkOnboardTest() throws Exception {
    }

    clickOnboard clinkonboard = new clickOnboard();
    clickOnboardNegative clinkonboardnegative = new clickOnboardNegative();

    @SneakyThrows
    @Test(priority = 1)
    @Description("It will onboard in click service")
    public void clinkOnboard() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        String clickonboardrequest = clickOnboardRequestPayload();
        clinkOnboardResponse clinkonboardresponse = clinkonboard.post(queryParamDetails, headerDetails, clickonboardrequest);
        Assert.assertNotNull(clinkonboardresponse.getUuid());
        int isMobileVerified = clinkonboardresponse.getIsMobileVerified();
        Assert.assertEquals(isMobileVerified, 1);

    }

    @SneakyThrows
    @Test(priority = 2)
    @Description("It will give error msg as invalid mobile number")
    public void clinkOnboardwithWrongMobileNumner() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        String clickonboardrequest = clickOnboardReqPayloadWrongMobileNumber();
        ClicnkOnboardResponseNegative clinkonboardresponse = clinkonboardnegative.post(queryParamDetails, headerDetails, clickonboardrequest);
        int status = clinkonboardresponse.getStatus();
        String errorMessage = clinkonboardresponse.getMessage();
        String errorCode = clinkonboardresponse.getErrorCode();
        Assert.assertEquals(status, 400);
        Assert.assertEquals(errorMessage, "Oops!! Entered mobile number seems to be invalid");
        Assert.assertEquals(errorCode, "LP_INVALID_MOBILE");

    }

    @SneakyThrows
    @Test(priority = 3)
    @Description("It will give error message as invalid source")
    public void clinkOnboardwithWrongSource() {
        long id = Thread.currentThread().getId();
        log.info("Simple test-method One. Thread id is: " + id);
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        String clickonboardrequest = clickOnboardReqPayloadWrongsource();
        ClicnkOnboardResponseNegative clinkonboardresponse = clinkonboardnegative.post(queryParamDetails, headerDetails, clickonboardrequest);
        int status = clinkonboardresponse.getStatus();
        String errorMessage = clinkonboardresponse.getMessage();
        String errorCode = clinkonboardresponse.getErrorCode();
        Assert.assertEquals(status, 400);
        Assert.assertEquals(errorMessage, "The source given is invalid");
        Assert.assertEquals(errorCode, "INVALID_SOURCE");

    }


}
