package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.DeleteSensitiveInfoPojo;

import java.util.HashMap;

public class DeleteSensitiveInfo extends BaseAPI<DeleteSensitiveInfoPojo> {

    public DeleteSensitiveInfo() throws Exception {
        super(Uri.DELETE_SENSITIVE_INFO, DeleteSensitiveInfoPojo.class);
    }

    @Step
    public DeleteSensitiveInfoPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }
}
