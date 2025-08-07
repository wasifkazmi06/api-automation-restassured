package api.userRegistration;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.userRegistration.FetchOTPPojo;
import java.util.HashMap;
import java.util.Map;

public class FetchOTP extends BaseAPI<FetchOTPPojo> {

    public FetchOTP() throws Exception {
        super(Uri.FETCH_OTP, FetchOTPPojo.class);
    }
    @Step
    public FetchOTPPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }}

