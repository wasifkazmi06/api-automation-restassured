package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.InitiateIRSPojo;
import java.util.HashMap;

public class InitiateIRS extends BaseAPI<InitiateIRSPojo> {
    public InitiateIRS() throws Exception {
            super(Uri.INITIATE_IRS, InitiateIRSPojo.class);
    }
    @Step
    public InitiateIRSPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails, request);
    }
}
