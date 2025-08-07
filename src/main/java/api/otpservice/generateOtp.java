package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.generateOtpResponsePojo;

import java.util.HashMap;

public class generateOtp extends BaseAPI<generateOtpResponsePojo> {
    public generateOtp() throws Exception {
        super(Uri.OTP_SERVICE_GENERATE_OTP, generateOtpResponsePojo.class);
    }

    @Step
    public generateOtpResponsePojo post(HashMap<String, Object> queryparamdetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryparamdetails, headerDetails, jsonRequestBody);

    }
}
