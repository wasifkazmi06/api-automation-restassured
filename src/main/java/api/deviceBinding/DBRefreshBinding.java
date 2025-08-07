package api.devicebinding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.bbpsInitiateOtpResponse;

import java.util.HashMap;
import java.util.Map;

public class DBRefreshBinding extends BaseAPI<bbpsInitiateOtpResponse> {
    public DBRefreshBinding() throws Exception {
        super(Uri.DB_REFRESH_BINDING, bbpsInitiateOtpResponse.class);
    }
    @Step
    public bbpsInitiateOtpResponse post(HashMap<String, Object> queryparamdetails,HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryparamdetails,headerDetails, jsonRequestBody);
    }
    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
