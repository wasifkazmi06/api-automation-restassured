package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.OauthTokenResponse;
import pojos.ams.oauthTokenWrongOtpResponse;

import java.util.HashMap;

public class oauthTokenError extends BaseAPI<oauthTokenWrongOtpResponse> {
    public oauthTokenError() throws Exception {
        super(Uri.PLATFORM_OAUTH_TOKEN, oauthTokenWrongOtpResponse.class);
    }
    @Step
    public oauthTokenWrongOtpResponse postWithFormParam(HashMap<String, Object> headerDetails, HashMap<String, Object> formparams) {
        return super.postWithFormParam(headerDetails, formparams);
    }
}

