package api.nach.presentment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.InitiateIRSPojo;
import pojos.nach.presentment.InitiateCollectionPojo;

import java.util.HashMap;

public class InitiateCollection extends BaseAPI<InitiateCollectionPojo> {
    public InitiateCollection() throws Exception {
            super(Uri.INITIATE_COLLECTION, InitiateCollectionPojo.class);
    }
    @Step
    public InitiateCollectionPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails, request);
    }
}


