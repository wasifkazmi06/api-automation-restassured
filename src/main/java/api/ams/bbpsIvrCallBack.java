package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.bbpsCallBackResponse;

import java.util.HashMap;
import java.util.Map;

public class bbpsIvrCallBack extends BaseAPI<bbpsCallBackResponse> {

    public bbpsIvrCallBack() throws Exception {
        super(Uri.PLATFORM_BBPS_CALL_BACK,bbpsCallBackResponse.class );
    }
    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }
    @Step
    public bbpsCallBackResponse post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}





