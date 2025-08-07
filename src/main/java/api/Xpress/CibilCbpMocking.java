package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.Xpress.CibilCbpResponsePojo;

import java.util.HashMap;

public class CibilCbpMocking extends BaseAPI<CibilCbpResponsePojo> {

    public CibilCbpMocking() throws Exception{
        super(Uri.BUREAU_CIBIL_CBP_MOCK, CibilCbpResponsePojo.class);
    }

    @Step
    public String postWithNoResponseBody(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithNoResponseBody(headerDetails, jsonRequestBody);
    }
}
