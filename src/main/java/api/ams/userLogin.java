package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.PlatformLoginResponsePojo;

import java.util.HashMap;
import java.util.Map;


public class userLogin extends BaseAPI<PlatformLoginResponsePojo> {
    public userLogin() throws Exception {
        super(Uri.PLATFORM_LOGIN, PlatformLoginResponsePojo.class);
    }

    @Step
    public PlatformLoginResponsePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }

}
