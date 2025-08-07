package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.ValidateOTPPojo;
import java.util.HashMap;
import java.util.Map;

public class ValidateOTP extends BaseAPI<ValidateOTPPojo> {
    public ValidateOTP() throws Exception {
        super(Uri.VALIDATE_OTP, ValidateOTPPojo.class);
    }
    @Step
    public ValidateOTPPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}