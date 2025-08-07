package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.BankVerifyResponsePojo;
import pojos.Xpress.OnboardingCaseStatusResponsePojo;

import java.util.HashMap;

public class SkipRepayment extends BaseAPI<OnboardingCaseStatusResponsePojo> {

    public SkipRepayment() throws Exception {
        super(Uri.SKIP_REPAYMENT_SHYLOCK, OnboardingCaseStatusResponsePojo.class);
    }

    @Step
    public OnboardingCaseStatusResponsePojo putWithPathParams(String pathParam, HashMap<String, String> headerDetails) {
        return super.putWithPathParams(headerDetails, pathParam);
    }
}
