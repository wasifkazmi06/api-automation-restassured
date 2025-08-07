package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.validateOtpResponsePojo;

import java.util.HashMap;

public class validateOtp extends BaseAPI<validateOtpResponsePojo> {
    public validateOtp() throws Exception{
        super(Uri.OTP_SERVICE_VALIDATE_OTP,validateOtpResponsePojo.class);
    }
    @Step
    public validateOtpResponsePojo post(HashMap<String, Object> queryparamdetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryparamdetails, headerDetails, jsonRequestBody);
    }
}
