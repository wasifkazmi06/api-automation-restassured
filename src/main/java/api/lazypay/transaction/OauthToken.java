package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.FindUserOTPPojo;
import pojos.lazypay.transactionFlow.OAuthTokenPojo;

import java.util.HashMap;
import java.util.Map;

public class OauthToken extends BaseAPI<OAuthTokenPojo> {
    public OauthToken() throws Exception {
        super(Uri.OAUTH_TOKEN, OAuthTokenPojo.class);
    }
    @Step
    public OAuthTokenPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}

