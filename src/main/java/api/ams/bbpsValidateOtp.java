package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.ams.bbpsValidateOtpResponse;

import java.util.HashMap;

public class bbpsValidateOtp extends BaseAPI<bbpsValidateOtpResponse> {
    public bbpsValidateOtp() throws Exception {
        super(Uri.PLATFORM_BBPS_VALIDATE_OTP, bbpsValidateOtpResponse.class);
    }
    @Step
    public bbpsValidateOtpResponse post(HashMap<String, Object> queryparamdetails,HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryparamdetails,headerDetails, jsonRequestBody);
    }
}
