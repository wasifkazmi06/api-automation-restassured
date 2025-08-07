package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.ClicnkOnboardResponseNegative;

import java.util.HashMap;
import java.util.Map;

public class clickOnboardNegative extends BaseAPI<ClicnkOnboardResponseNegative> {
    public clickOnboardNegative() throws Exception {
        super(Uri.CLINK_ONBOARD, ClicnkOnboardResponseNegative.class);
    }

    @Step
    public ClicnkOnboardResponseNegative post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}

