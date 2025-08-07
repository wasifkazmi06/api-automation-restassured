package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.OauthTokenResponse;
import java.util.HashMap;



public class oauthToken extends BaseAPI<OauthTokenResponse> {
    public oauthToken() throws Exception {
        super(Uri.PLATFORM_OAUTH_TOKEN, OauthTokenResponse.class);
    }
    @Step
    public OauthTokenResponse postWithFormParam(HashMap<String, Object> headerDetails, HashMap<String, Object> formparams) {
        return super.postWithFormParam(headerDetails, formparams);
    }
}
