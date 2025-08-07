package deviceInfo;

import dbUtils.Device_Binding_MySQL_DBAccessObject;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.util.HashMap;
@Slf4j
public class DeviceInfoData {

    public static final String POLICYID1="74"; // For this policy, device will expire after 1000 hours and 100 devices can be stored for each platform
    public static final String POLICYID2="79";
    public static final String POLICYID3="77"; // For this policy, device will expire after 50 hours and 3 devices can be stored for each platform

    // Command to connect Device Info DB
    private static final String DELETE_DEVICE_FROM_DEVICE_INFO= "DELETE FROM device.device_info WHERE source_device_id='$' AND tenant_user_id='&';";
    private static String SELECT_DEVICE_FROM_DEVICE_INFO="select * from device.device_info WHERE source_device_id='$' AND tenant_user_id='&'";




    public static void deleteDeviceDataFromDeviceInfo(HashMap<String, String> request) {
        try {
            String deleteQuery=(DELETE_DEVICE_FROM_DEVICE_INFO.replace("$", request.get("deviceId")).replace("&", request.get("lpUserId")));
            log.info("delete device query from device info{}", deleteQuery);
            Device_Binding_MySQL_DBAccessObject.deleteOnMySqlDb(deleteQuery);
        } catch (Exception e) {
            log.debug("Device Info Entry is not present" + e);
        }
    }

    public static void selectDeviceDataFromDeviceInfo(String tennantUserID, String sourceDeviceID) {
        try {
            String selectQuery=(SELECT_DEVICE_FROM_DEVICE_INFO.replace("$", sourceDeviceID).replace("&", tennantUserID));
            log.info("select device query from device info{}", selectQuery);
            ResultSet deviceInfo1 = Device_Binding_MySQL_DBAccessObject.selectFromMySqlDb(selectQuery);
            while(deviceInfo1.next())
            {
                String s1 =deviceInfo1.getString("source_device_id");
                System.out.println(s1);
            }

        } catch (Exception e) {
            log.debug("Device Info Entry is not present" + e);
        }

    }
}