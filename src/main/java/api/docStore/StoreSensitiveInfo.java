package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.StoreSensitiveInfoPojo;

import java.util.HashMap;

public class StoreSensitiveInfo extends BaseAPI<StoreSensitiveInfoPojo> {

    public StoreSensitiveInfo() throws Exception {
        super(Uri.STORE_SENSITIVE_INFO, StoreSensitiveInfoPojo.class);
    }

    @Step
    public StoreSensitiveInfoPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }
}
