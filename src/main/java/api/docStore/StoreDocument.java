package api.docStore;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.docStore.StoreDocumentPojo;

import java.util.HashMap;
import java.util.Map;

public class StoreDocument extends BaseAPI<StoreDocumentPojo> {

    public StoreDocument() throws Exception {
        super(Uri.STORE_DOCUMENT, StoreDocumentPojo.class);
    }

    @Step
    public StoreDocumentPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
