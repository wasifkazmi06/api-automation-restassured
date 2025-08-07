package api.userRegistration;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.validateUserPojo;

import java.util.HashMap;
import java.util.Map;

public class ResendOTP extends BaseAPI<validateUserPojo> {
    public ResendOTP() throws Exception {
        super(Uri.URS_RESEND_OTP, validateUserPojo.class);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }


}
