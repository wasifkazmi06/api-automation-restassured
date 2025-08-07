package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.SendOtpPojo;
import pojos.lazypay.juspay.repaymentFlow.VerifyOtpPojo;

import java.util.HashMap;
import java.util.Map;

public class VerifyOTP extends BaseAPI<VerifyOtpPojo> {
    public VerifyOTP() throws Exception {
        super(Uri.VERIFY_OTP, VerifyOtpPojo.class);
    }

    @Step
    public VerifyOtpPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }


}
