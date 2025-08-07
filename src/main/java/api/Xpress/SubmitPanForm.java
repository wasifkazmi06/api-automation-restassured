package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.Xpress.SubmitPanFormResponsePojo;

import java.util.HashMap;

public class SubmitPanForm extends BaseAPI<SubmitPanFormResponsePojo> {

    public SubmitPanForm() throws Exception {
        super(Uri.SUBMIT_PAN_FORM_SHYLOCK, SubmitPanFormResponsePojo.class);
    }

    @Step
    public SubmitPanFormResponsePojo postWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithoutQueryParams(headerDetails, jsonRequestBody);
    }
}
