package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.UpdateSensitiveInfoPojo;

import java.util.HashMap;

public class UpdateSensitiveInfo extends BaseAPI<UpdateSensitiveInfoPojo> {

    public UpdateSensitiveInfo() throws Exception {
        super(Uri.UPDATE_SENSITIVE_INFO, UpdateSensitiveInfoPojo.class);
    }

    @Step
    public UpdateSensitiveInfoPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }
}
