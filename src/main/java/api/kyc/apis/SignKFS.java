package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.kyc.apis.BifrostResolveStepsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class SignKFS extends BaseAPI<BifrostResolveStepsResponsePojo> {
    public SignKFS() throws Exception {
        super(Uri.PLATFORM_SIGN_KFS, BifrostResolveStepsResponsePojo.class);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails,jsonRequestBody);
    }

}
