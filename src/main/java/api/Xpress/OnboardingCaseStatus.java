package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class OnboardingCaseStatus extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public OnboardingCaseStatus() throws Exception {
        super(Uri.ONBOARDING_STATUS_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }

    @Step
    public OnboardingCaseStatusResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}
