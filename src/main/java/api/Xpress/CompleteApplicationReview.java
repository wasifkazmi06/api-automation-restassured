package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;

public class CompleteApplicationReview extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public CompleteApplicationReview() throws Exception {
        super(Uri.COMPLETE_APPLICATION_REVIEW_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public Response postWithResponseBody(String pathParam, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponseBody(pathParam, headerDetails, jsonRequestBody);
    }
}
