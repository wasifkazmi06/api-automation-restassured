package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.PlatformLoginResponsePojo;

import java.util.HashMap;
import java.util.Map;


public class V1UserLogin extends BaseAPI<PlatformLoginResponsePojo> {
    public V1UserLogin() throws Exception {
        super(Uri.PLATFORM_V1_LOGIN, PlatformLoginResponsePojo.class);
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
