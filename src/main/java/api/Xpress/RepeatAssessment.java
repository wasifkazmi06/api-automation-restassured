package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.RepeatAssessmentResponsePojo;

import java.util.HashMap;

public class RepeatAssessment extends BaseAPI<RepeatAssessmentResponsePojo> {
    public RepeatAssessment() throws Exception {
        super(Uri.REPEAT_ASSESSMENT_ASSESSMENT_ENGINE, RepeatAssessmentResponsePojo.class);
    }

    @Step
    public Response postWithResponse(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(headerDetails, jsonRequestBody);
    }
}
