package api.kyc.loginEssentials;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.loginEssentials.OtpPojo;

import java.util.HashMap;
import java.util.Map;

public class GetOtp extends BaseAPI<OtpPojo> {
    public GetOtp() throws Exception {
        super(Uri.OTP, OtpPojo.class);
    }
    @Step
    public OtpPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
