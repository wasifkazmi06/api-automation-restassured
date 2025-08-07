package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.OauthTokenResponse;
import pojos.ams.oauthTokenWrongOtpResponse;

import java.util.HashMap;

public class verifyMobileSignInError extends BaseAPI<oauthTokenWrongOtpResponse> {
    public verifyMobileSignInError() throws Exception {
        super(Uri.PLATFORM_VERIFY_MOBILE_SIGIN_IN, oauthTokenWrongOtpResponse.class);
    }
    @Step
    public oauthTokenWrongOtpResponse postWithQueryParam(HashMap<String, Object> queryParamDetails,HashMap<String, Object> headerDetails) {
        return super.postWithQueryParam(queryParamDetails,headerDetails);
    }
}
