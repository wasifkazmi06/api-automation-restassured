package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.RetrieveSensitiveInfoPojo;

import java.util.HashMap;

public class RetrieveSensitiveInfo extends BaseAPI<RetrieveSensitiveInfoPojo> {

    public RetrieveSensitiveInfo() throws Exception {
        super(Uri.RETRIEVE_SENSITIVE_INFO, RetrieveSensitiveInfoPojo.class);
    }

    @Step
    public RetrieveSensitiveInfoPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }
}
