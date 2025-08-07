package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;

public class AgreementSendOtp extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public AgreementSendOtp() throws Exception {
        super(Uri.AGREEMENT_SENT_OTP_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public Response postWithResponse(String jsonRequestBody, HashMap<String, String> headerDetails) {
        return super.postWithResponse(headerDetails, jsonRequestBody);
    }
}
