package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class SubmitCaForm extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public SubmitCaForm() throws Exception {
        super(Uri.SUBMIT_CA_FORM_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public Response postWithResponseBody(String pathParam,HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponseBody(pathParam,headerDetails,jsonRequestBody);
    }
}
