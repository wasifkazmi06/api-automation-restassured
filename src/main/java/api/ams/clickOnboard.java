package api.ams;


import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.clinkOnboardResponse;

import java.util.HashMap;
import java.util.Map;

public class clickOnboard extends BaseAPI<clinkOnboardResponse> {
    public clickOnboard() throws Exception {
        super(Uri.CLINK_ONBOARD, clinkOnboardResponse.class);
    }

    @Step
    public clinkOnboardResponse post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
