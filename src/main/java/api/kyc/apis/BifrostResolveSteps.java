package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.BifrostResolveStepsResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class BifrostResolveSteps extends BaseAPI<BifrostResolveStepsResponsePojo> {
    public BifrostResolveSteps() throws Exception {
        super(Uri.RESOLVE_STEPS, BifrostResolveStepsResponsePojo.class);
    }

    @Step
    public BifrostResolveStepsResponsePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
