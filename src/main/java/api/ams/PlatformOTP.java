package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.PlatformOtpPojo;

import java.util.HashMap;
import java.util.Map;

public class PlatformOTP extends BaseAPI<PlatformOtpPojo> {
    public PlatformOTP() throws Exception {
        super(Uri.PLATFORM_OTP, PlatformOtpPojo.class);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }

}
