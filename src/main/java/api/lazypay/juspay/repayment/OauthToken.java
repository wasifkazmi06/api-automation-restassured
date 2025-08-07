package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.OauthTokenPojo;

import java.util.HashMap;
import java.util.Map;

public class OauthToken extends BaseAPI<OauthTokenPojo> {
    public OauthToken() throws Exception {
        super(Uri.OAUTH_TOKEN, OauthTokenPojo.class);
    }
    @Step
    public OauthTokenPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
