package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.OauthTokenResponse;

import java.util.HashMap;

public class verifyMobileSignIn extends BaseAPI<OauthTokenResponse> {
    public verifyMobileSignIn() throws Exception {
        super(Uri.PLATFORM_VERIFY_MOBILE_SIGIN_IN, OauthTokenResponse.class);
    }
    @Step
    public OauthTokenResponse postWithQueryParam(HashMap<String, Object> queryParamDetails,HashMap<String, Object> headerDetails) {
        return super.postWithQueryParam(queryParamDetails,headerDetails);
    }
}
