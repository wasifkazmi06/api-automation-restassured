package upi.registration;

import api.upi.registration.RegistrationStatus;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.upi.registration.RegistrationStatusPojo;
import upi.UPIData;
import java.util.HashMap;
import java.util.Map;

public class RegistrationStatusSteps {

    public static RegistrationStatusPojo registrationStatusPojo = new RegistrationStatusPojo();
    RegistrationStatus registrationStatus =  new RegistrationStatus();

    public RegistrationStatusSteps() throws Exception {
    }

    @Step
    public void getUPIRegistrationStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simId", UPIData.SIM_ID);
        queryParamDetails.put("mobile", UPIData.UPI_USER);
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "ANDROID");


        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

    }

    @Step
    public void verifyUPIRegistrationStatusForNewUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simId", UPIData.SIM_ID);
        queryParamDetails.put("mobile", UPIData.UPI_USER);
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "ANDROID");


        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(registrationStatusPojo.upiStatus, "NOT_REGISTERED", "Make sure the user has not already started the registration or registered for UPI");


    }
    @Step
    public void verifyUPIPendingRegistrationStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simid", UPIData.SIM_ID);
        queryParamDetails.put("mobile", UPIData.UPI_USER);
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "ANDROID");

        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);
        Assert.assertTrue(registrationStatusPojo.upiStatus.equals("REGISTERED_PENDING_ACTIVATION") || registrationStatusPojo.upiStatus.equals("REGISTRATION_IN_PROGRESS"), "Make sure the user has not already registered for UPI");
    }

    @Step
    public void verifyUPIRegistrationStatusForRegisteredUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simId", UPIData.SIM_ID);
        queryParamDetails.put("mobile", UPIData.UPI_USER);
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "ANDROID");

        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(registrationStatusPojo.upiStatus, "REGISTERED", "Make sure user's upi_user status is REGISTERED or check that notify-SMS API has run correctly");

    }

    @Step
    public void verifyMandatoryValidationOnMobileForGetRegistrationStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simid", UPIData.SIM_ID);
        queryParamDetails.put("mobile", "");
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "ANDROID");

        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(registrationStatusPojo.errorCode, "INVALID_MOBILE", "Make sure mobile is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(registrationStatusPojo.error, "Bad Request", "Make sure mobile is not passed in the request");
    }

    @Step
    public void verifyFormatValidationOnMobileForGetRegistrationStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simid", UPIData.SIM_ID);
        queryParamDetails.put("mobile", "aabs@$d12");
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "ANDROID");

        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(registrationStatusPojo.errorCode, "INVALID_MOBILE", "Make sure sim ID is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(registrationStatusPojo.error, "Bad Request", "Make sure sim ID is not passed in the request");
    }

    @Step
    public void verifyMandatoryValidationOnSourceForGetRegistrationStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simid", UPIData.SIM_ID);
        queryParamDetails.put("mobile", UPIData.UPI_USER);
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "");

        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(registrationStatusPojo.errorCode, "INVALID_SOURCE", "Make sure source is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(registrationStatusPojo.error, "Bad Request", "Make sure source is not passed in the request");
    }

    @Step
    public void verifyKeyValueValidationOnSourceForGetRegistrationStatus() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("simid", UPIData.SIM_ID);
        queryParamDetails.put("mobile", UPIData.UPI_USER);
        queryParamDetails.put("deviceId", UPIData.DEVICE_ID);
        queryParamDetails.put("source", "R2D2");

        registrationStatusPojo = registrationStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(registrationStatusPojo.errorCode, "INVALID_SOURCE","Make sure an incorrect source is passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(registrationStatusPojo.error, "Bad Request", "Make sure an incorrect source is passed in the request");
    }
}
