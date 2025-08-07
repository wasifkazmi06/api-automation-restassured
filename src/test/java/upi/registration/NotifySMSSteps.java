package upi.registration;

import api.upi.registration.NotifySMS;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.upi.registration.NotifySMSPojo;
import upi.UPIConstants;
import upi.UPIData;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class NotifySMSSteps {

    NotifySMSPojo notifySMSPojo = new NotifySMSPojo();
    NotifySMS notifySMS= new NotifySMS();

    public NotifySMSSteps() throws Exception {
    }

    @Step
    public void verifyNotifySMSForNewUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .replace("status", "SUCCESS")
                .replace("upiRegistrationId", GenerateSMSSteps.generateSMSPojo.upiRegistrationId )
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.notifyStatus ,"SUCCESS", "Make sure the generated registration ID has been passed in the request");
    }

    @Step
    public void verifyMandatoryValidationOnDeviceIDForNotifySMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", "")
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .replace("status", "SUCCESS")
                .replace("upiRegistrationId", "12345" )
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.error ,"Bad Request", "Make sure device ID is not passed in the request");
        Assert.assertEquals(notifySMSPojo.errorCode ,"INVALID_DEVICE_ID", "Make sure device ID is not passed in the request or if the error code has been changed by the developer");
    }

    @Step
    public void verifyMandatoryValidationOnMobileForNotifySMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", "")
                .replace("simId", UPIData.SIM_ID)
                .replace("status", "SUCCESS")
                .replace("upiRegistrationId", "12345")
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.error ,"Bad Request", "Make sure mobile is not passed in the request");
        Assert.assertEquals(notifySMSPojo.errorCode ,"INVALID_MOBILE", "Make sure mobile is not passed in the request or if the error code has been changed by the developer");
    }
    @Step
    public void verifyMandatoryValidationOnSimIDForNotifySMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", "")
                .replace("status", "SUCCESS")
                .replace("upiRegistrationId", "12345" )
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.error ,"Bad Request", "Make sure sim ID is not passed in the request");
        Assert.assertEquals(notifySMSPojo.errorCode ,"INVALID_SIM_ID", "Make sure sim ID is not passed in the request or if the error code has been changed by the developer");
    }

    @Step
    public void verifyMandatoryValidationOnStatusForNotifySMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .replace("status", "")
                .replace("upiRegistrationId", "12345" )
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.error ,"Bad Request", "Make sure status is not passed in the request");
        Assert.assertEquals(notifySMSPojo.errorCode ,"INVALID_NOTIFY_SMS_STATUS", "Make sure status is not passed in the request or if the error code has been changed by the developer");
    }

    @Step
    public void verifyKeyValueValidationOnStatusForNotifySMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .replace("status", "IncorrectStatus")
                .replace("upiRegistrationId", "12345" )
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.error ,"Bad Request", "Make sure incorrect status is passed in the request");
        Assert.assertEquals(notifySMSPojo.errorCode ,"INVALID_UPI_REGISTRATION_STATUS", "Make sure incorrect status is passed in the request or if the error code has been changed by the developer");
    }

    @Step
    public void verifyIncorrectRegistrationIDValidationForNotifySMS() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String notifySMSRequest = new StringTemplate(UPIConstants.NOTIFY_SMS)
                .replace("deviceId", UPIData.DEVICE_ID)
                .replace("mobile", UPIData.UPI_USER)
                .replace("simId", UPIData.SIM_ID)
                .replace("status", "SUCCESS")
                .replace("upiRegistrationId", "1" )
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, notifySMSRequest);
        Assert.assertEquals(notifySMSPojo.error ,"Bad Request", "Make sure incorrect registration ID is passed in the request");
        Assert.assertEquals(notifySMSPojo.errorCode ,"INVALID_UPI_REGISTRATION_STATUS", "Make sure incorrect registration ID is passed in the request or if the error code has been changed by the developer");
    }
}
