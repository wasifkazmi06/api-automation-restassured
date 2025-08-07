package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.AuthenticateSetupNBPojo;
import java.util.HashMap;

public class AuthenticateSetupNB extends BaseAPI<AuthenticateSetupNBPojo> {
    public AuthenticateSetupNB() throws Exception {
            super(Uri.AUTHENTICATE_SETUP_NB, AuthenticateSetupNBPojo.class);
    }
    @Step
    public AuthenticateSetupNBPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails, request);
    }
}


