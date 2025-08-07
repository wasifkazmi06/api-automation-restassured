package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.ScrubAssessmentResponsePojo;

import java.util.HashMap;

public class ScrubAssessment extends BaseAPI<ScrubAssessmentResponsePojo> {

    public ScrubAssessment() throws Exception {
        super(Uri.SCRUB_ASSESSMENT_ENGINE, ScrubAssessmentResponsePojo.class);
    }

    @Step
    public Response postWithResponse(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(headerDetails, jsonRequestBody);
    }
}
