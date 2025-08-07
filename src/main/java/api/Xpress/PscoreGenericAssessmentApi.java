package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.PscoreGenericAssessmentResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class PscoreGenericAssessmentApi extends BaseAPI<PscoreGenericAssessmentResponsePojo> {

    public PscoreGenericAssessmentApi() throws Exception {
        super(Uri.PSCORE_GENERIC_ASSESSMENT, PscoreGenericAssessmentResponsePojo.class);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }
}
