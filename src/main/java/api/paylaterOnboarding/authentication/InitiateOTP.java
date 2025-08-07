package api.paylaterOnboarding.authentication;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.paylaterOnboarding.PLInitiateOTPPojo;

import java.util.HashMap;
import java.util.Map;

public class InitiateOTP extends BaseAPI<PLInitiateOTPPojo> {
    public InitiateOTP() throws Exception {
        super(Uri.PL_INITIATE_OTP, PLInitiateOTPPojo.class);
    }
    @Step
    public PLInitiateOTPPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
