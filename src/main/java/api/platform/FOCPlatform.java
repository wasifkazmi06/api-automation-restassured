package api.platform;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.FindUserOTPPojo;
import pojos.platform.FOCPlatformPojo;

import java.util.HashMap;
import java.util.Map;

public class FOCPlatform extends BaseAPI<FOCPlatformPojo> {
    public FOCPlatform() throws Exception {
        super(Uri.FOC_PLATFORM, FOCPlatformPojo.class);
    }
    @Step
    public FOCPlatformPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}

