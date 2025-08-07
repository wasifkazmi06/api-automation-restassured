package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.GetOtpResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class GetOtp extends BaseAPI<GetOtpResponsePojo> {
    public GetOtp() throws Exception {
        super(Uri.OTP_SERVICE_GET_OTP, GetOtpResponsePojo.class);
    }

    @Step
    public GetOtpResponsePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);

    }
}
