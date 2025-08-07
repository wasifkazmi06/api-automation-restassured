package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.Xpress.AssessmentEngineMockResponsePojo;

import java.util.HashMap;

public class DarwinV6Mocking extends BaseAPI<AssessmentEngineMockResponsePojo> {

    public DarwinV6Mocking() throws Exception{
        super(Uri.DARWIN_FEATURES_MOCK, AssessmentEngineMockResponsePojo.class);
    }

    @Step
    public AssessmentEngineMockResponsePojo postWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithoutQueryParams(headerDetails, jsonRequestBody);
    }
}
