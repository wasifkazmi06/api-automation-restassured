package devicebinding;

import api.deviceBinding.DeviceBindingWebhook;
import api.deviceBinding.GenerateSMS;
import api.deviceBinding.GetRegistrationStatus;
import api.deviceBinding.NotifySMS;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.deviceBinding.GenerateSMSPojo;
import pojos.deviceBinding.GetRegistrationStatusPojo;
import pojos.deviceBinding.NotifySMSPojo;
import util.StringTemplate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import static lazypay.MakeTransaction.*;

@Slf4j
public class DeviceBinding {


    public static String deviceId = System.getProperty("deviceId");
    public static String simId = System.getProperty("simId");
    public static String sourceDeviceBind = System.getProperty("sourceDeviceBind");

    public static GetRegistrationStatusPojo getRegistrationStatusPojo = new GetRegistrationStatusPojo();
    public static GetRegistrationStatus getRegistrationStatus;
    public static GenerateSMSPojo generateSMSPojo = new GenerateSMSPojo();
    public static GenerateSMS generateSMS;
    public static NotifySMSPojo notifySMSPojo = new NotifySMSPojo();
    public static NotifySMS notifySMS;
    public static Response deviceBindingWebhookResponse;
    public static DeviceBindingWebhook deviceBindingWebhook;

    static {
        try {
            getRegistrationStatus = new GetRegistrationStatus();
            generateSMS = new GenerateSMS();
            notifySMS = new NotifySMS();
            deviceBindingWebhook = new DeviceBindingWebhook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void bindDevice() {

        getRegistrationStatus(mobile, deviceId, simId,sourceDeviceBind);
        if(getRegistrationStatusPojo.status.equals(DeviceBindingData.STATUS_REGISTERED)){
            log.info("User {} is already registered with given device and sim ID.", mobile);
            return;
        }else{
            generateSMSMethod(mobile, deviceId, simId, sourceDeviceBind);
            Assert.assertNotNull(generateSMSPojo.deviceBindingId);
            Assert.assertNotNull(generateSMSPojo.smsContent);
            Assert.assertNotNull(generateSMSPojo.recipientNumber);

            notifySMSMethod(mobile, deviceId, simId, String.valueOf(generateSMSPojo.deviceBindingId), DeviceBindingData.STATUS_SUCCESS_NOTIFY_SMS);
            Assert.assertEquals(notifySMSPojo.notifyStatus, DeviceBindingData.STATUS_SUCCESS_NOTIFY_SMS);

            deviceBindingWebhook(mobile, generateSMSPojo.smsContent, String.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
            Assert.assertEquals(deviceBindingWebhookResponse.getStatusCode(), 200);

            getRegistrationStatus(mobile, deviceId, simId, sourceDeviceBind);
            Assert.assertEquals(getRegistrationStatusPojo.status, DeviceBindingData.STATUS_REGISTERED);
        }
    }


    public void getRegistrationStatus(String mobile, String deviceId, String simId, String sourceDeviceBind) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("mobile", mobile);
        queryParamDetails.put("deviceIds", deviceId);
        queryParamDetails.put("simIds", simId);
        queryParamDetails.put("source", sourceDeviceBind);

        getRegistrationStatusPojo = getRegistrationStatus.get(queryParamDetails, headerDetails);
    }

    public void generateSMSMethod(String mobile, String deviceId, String simId, String sourceDeviceBind) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

       String generateSMSRequest = new StringTemplate(DeviceBindingConstants.GENERATE_SMS_REQUEST)
               .replace("mobile", mobile)
               .replace("deviceIds", deviceId)
               .replace("simIds", simId)
               .replace("source", sourceDeviceBind)
               .toString();

        generateSMSPojo = generateSMS.post(queryParamDetails, headerDetails, generateSMSRequest);
    }

    public void notifySMSMethod(String mobile, String deviceId, String simId, String deviceBindingId, String status) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String generateSMSRequest = new StringTemplate(DeviceBindingConstants.GENERATE_SMS_REQUEST)
                .replace("mobile", mobile)
                .replace("deviceIds", deviceId)
                .replace("simIds", simId)
                .replace("deviceBindingId", deviceBindingId)
                .replace("status", status)
                .toString();

        notifySMSPojo = notifySMS.post(queryParamDetails, headerDetails, generateSMSRequest);
    }

    public void deviceBindingWebhook(String sender, String msg, String sent_date_time) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("sender", sender);
        headerDetails.put("msg", msg);
        headerDetails.put("sent_date_time", sent_date_time);

        deviceBindingWebhookResponse = deviceBindingWebhook.postReturnResponse(queryParamDetails, headerDetails);
    }

}
