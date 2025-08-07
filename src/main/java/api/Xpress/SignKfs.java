package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;

public class SignKfs extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public SignKfs() throws Exception {
        super(Uri.SIGN_KFS_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public Response postWithPathParamAndResponse(String pathParam, HashMap<String, String> headerDetails) {
        return super.postWithPathParamAndResponse(headerDetails, pathParam);
    }
}
