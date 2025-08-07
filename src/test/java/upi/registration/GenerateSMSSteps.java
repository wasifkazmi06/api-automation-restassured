package upi.registration;

import api.upi.registration.GenerateSMS;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.upi.registration.GenerateSMSPojo;
import upi.UPIConstants;
import upi.UPIData;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class GenerateSMSSteps {

    public static GenerateSMSPojo generateSMSPojo = new GenerateSMSPojo();
    GenerateSMS generateSMS = new GenerateSMS();

    public GenerateSMSSteps() throws Exception {
    }

    @Step
    public void verifySendSMSForNewUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String generateSMSRequest = new StringTemplate(UPIConstants.GENERATE_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .toString();

        generateSMSPojo = generateSMS.post(queryParamDetails, headerDetails, generateSMSRequest);
        Assert.assertNotNull(generateSMSPojo.upiRegistrationId, "Make sure if a registration ID has been generated or not");


    }

    @Step
    public void verifyMandatoryValidationOnDeviceIDForGenerateSMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String generateSMSRequest = new StringTemplate(UPIConstants.GENERATE_SMS)
                .replace("deviceId", "")
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .toString();

        generateSMSPojo = generateSMS.post(queryParamDetails, headerDetails, generateSMSRequest);
        Assert.assertEquals(generateSMSPojo.error,"Bad Request", "Make sure device ID is not passed in the request");
        Assert.assertEquals(generateSMSPojo.errorCode,"INVALID_DEVICE_ID", "Make sure device ID is not passed in the request or if the error code has been changed by the developer");

    }

    @Step
    public void verifyMandatoryValidationOnMobileForGenerateSMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String generateSMSRequest = new StringTemplate(UPIConstants.GENERATE_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", "")
                .replace("simId", UPIData.SIM_ID)
                .toString();

        generateSMSPojo = generateSMS.post(queryParamDetails, headerDetails, generateSMSRequest);
        Assert.assertEquals(generateSMSPojo.error,"Bad Request", "Make sure mobile is not passed in the request");
        Assert.assertEquals(generateSMSPojo.errorCode,"INVALID_MOBILE", "Make sure mobile is not passed in the request or if the error code has been changed by the developer");
    }

    @Step
    public void verifyMandatoryValidationOnSimIDForGenerateSMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String generateSMSRequest = new StringTemplate(UPIConstants.GENERATE_SMS)
                .replace("deviceId", "TestDeviceID115")
                .replace("mobile", "6000000115")
                .replace("simId", "")
                .toString();

        generateSMSPojo = generateSMS.post(queryParamDetails, headerDetails, generateSMSRequest);
        Assert.assertEquals(generateSMSPojo.error,"Bad Request", "Make sure sim ID is not passed in the request");
        Assert.assertEquals(generateSMSPojo.errorCode,"INVALID_SIM_ID", "Make sure sim ID is not passed in the request or if the error code has been changed by the developer");
    }


}
