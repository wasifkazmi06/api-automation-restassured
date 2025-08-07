package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.Xpress.HardPullResponsePojo;

import java.util.HashMap;

public class CibilHardpullMocking extends BaseAPI<HardPullResponsePojo> {

    public CibilHardpullMocking() throws Exception{
        super(Uri.BUREAU_HARD_PULL_MOCK, HardPullResponsePojo.class);
    }

    @Step
    public String postWithNoResponseBody(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithNoResponseBody(headerDetails, jsonRequestBody);
    }

}
