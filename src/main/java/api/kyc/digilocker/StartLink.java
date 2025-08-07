package api.kyc.digilocker;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.digilocker.StartLinkPojo;

import java.util.HashMap;
import java.util.Map;

public class StartLink extends BaseAPI<StartLinkPojo> {

    public StartLink() throws Exception {
        super(Uri.START_DIGILOCKER, StartLinkPojo.class);
    }
    @Step
    public StartLinkPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}