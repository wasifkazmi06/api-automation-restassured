package api.deviceInfo;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.deviceInfo.CheckExistenceAPIResponsePojo;
import java.util.HashMap;


public class CheckExistenceRequest extends BaseAPI<CheckExistenceAPIResponsePojo> {
    public CheckExistenceRequest() throws Exception {
        super(Uri.CHECK_EXISTENCE, CheckExistenceAPIResponsePojo.class);
    }

    @Step
    public CheckExistenceAPIResponsePojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }
}