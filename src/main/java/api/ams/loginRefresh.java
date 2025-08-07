package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.RefreshTokenResponse;

import java.util.HashMap;
import java.util.Map;


public class loginRefresh extends BaseAPI<RefreshTokenResponse> {
    public loginRefresh() throws Exception {
        super(Uri.PLATFORM_LOGIN_REFRESH, RefreshTokenResponse.class);
    }

    @Step
    public RefreshTokenResponse post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
