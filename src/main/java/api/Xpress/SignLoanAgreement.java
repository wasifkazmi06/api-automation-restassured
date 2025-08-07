package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;

public class SignLoanAgreement extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public SignLoanAgreement() throws Exception {
        super(Uri.SIGN_LOAN_AGREEMENT_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public OnboardingCaseStatusResponsePojo put(String jsonReuestBody, HashMap<String, String> headerDetails) {
        return super.put(headerDetails, jsonReuestBody);
    }
}
