package deviceInfo;

import api.deviceInfo.CheckExistenceRequest;
import constants.UtilConstants;
import lombok.extern.slf4j.Slf4j;
import pojos.deviceInfo.CheckExistenceAPIResponsePojo;
import util.ReadProperties;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

import static deviceInfo.DeviceInfoData.*;
import static deviceInfo.DeviceServiceConstants.*;

@Slf4j
public class DeviceInfo {


    public static long currentTimeMillis;

    static CheckExistenceRequest checkExistenceRequest;
    public static CheckExistenceAPIResponsePojo checkExistenceAPIResponsePojo = new CheckExistenceAPIResponsePojo();
    public static long currentTimeInMilliseconds()
    {
        return currentTimeMillis = System.currentTimeMillis();
    }

    static {
        try {
            checkExistenceRequest = new CheckExistenceRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verifyDeviceInDeviceInfoTable(HashMap<String, String> request) throws Exception {
        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("PF-Tenant-Id",  ReadProperties.testConfigMap.get(UtilConstants.PFTenantId).toString());
        headerDetails.put("PF-Tenant-Token", ReadProperties.testConfigMap.get(UtilConstants.PFTenantToken).toString());
        String checkDeviceExistenceRequest = new StringTemplate(CHECK_EXISTENCE_API)
                .replace("deviceId", request.get("deviceId"))
                .replace("lpUserId", request.get("lpUserId"))
                .replace("accessKey", request.get("accessKey"))
                .replace("platform", request.get("platform"))
                .replace("policyId", request.get("policyId")).toString();
        checkExistenceAPIResponsePojo = checkExistenceRequest.post(headerDetails, checkDeviceExistenceRequest);
    }

}