package mbe.authentication;


import api.lazypay.juspay.repayment.DeviceConfig;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import pojos.lazypay.juspay.repaymentFlow.DevicConfigPojo;


import java.util.HashMap;
import java.util.Map;

public class DeviceConfigTest {


    public static DevicConfigPojo devicConfigPojo=new DevicConfigPojo();
    public static String currentAppVersion;
    static DeviceConfig deviceConfig;

    public DeviceConfigTest() throws Exception {
    }


    static {
        try {
            deviceConfig = new DeviceConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step

    public static String fetchUpdatedAppVersion() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("appversion", AuthTestData.appVersion);
        headerDetails.put("osversion", AuthTestData.osversion);
        headerDetails.put("deviceType", AuthTestData.deviceType);
        headerDetails.put("deviceversion", AuthTestData.deviceversion);
        headerDetails.put("userAgent",AuthTestData.userAgent);
        headerDetails.put("deviceId",AuthTestData.deviceId);
        headerDetails.put("location",AuthTestData.location);
        headerDetails.put("deviceIP",AuthTestData.deviceIP);
        headerDetails.put("requestId",AuthTestData.requestId);
        devicConfigPojo = deviceConfig.get(queryParamDetails, headerDetails);
        currentAppVersion=devicConfigPojo.appVersion;
        return currentAppVersion;
    }
}
