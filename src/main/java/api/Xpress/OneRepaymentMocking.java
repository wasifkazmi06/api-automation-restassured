package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.Xpress.AssessmentEngineMockResponsePojo;

import java.util.HashMap;

public class OneRepaymentMocking extends BaseAPI<AssessmentEngineMockResponsePojo> {

    public OneRepaymentMocking() throws Exception{
        super(Uri.ONE_REPAYMENT_FEATURES_MOCK, AssessmentEngineMockResponsePojo.class);
    }

    @Step
    public AssessmentEngineMockResponsePojo postWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithoutQueryParams(headerDetails, jsonRequestBody);
    }
}