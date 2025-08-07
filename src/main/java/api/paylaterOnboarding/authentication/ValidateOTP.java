package api.paylaterOnboarding.authentication;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.paylaterOnboarding.PLValidateOTPPojo;

import java.util.HashMap;
import java.util.Map;

public class ValidateOTP extends BaseAPI<PLValidateOTPPojo> {
    public ValidateOTP() throws Exception {
        super(Uri.PL_VALIDATE_OTP, PLValidateOTPPojo.class);
    }
    @Step
    public PLValidateOTPPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
