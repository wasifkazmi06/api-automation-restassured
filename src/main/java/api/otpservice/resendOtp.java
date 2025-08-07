package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.resendOtpResponsePojo;

import java.util.HashMap;

public class resendOtp extends BaseAPI<resendOtpResponsePojo> {
    public resendOtp() throws Exception{
        super(Uri.OTP_SERVICE_RESEND_OTP,resendOtpResponsePojo.class);
    }
    @Step
    public resendOtpResponsePojo post(HashMap<String, Object> queryparamdetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryparamdetails, headerDetails, jsonRequestBody);
    }
}
