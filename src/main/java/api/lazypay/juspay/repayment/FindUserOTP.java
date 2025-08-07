package api.lazypay.juspay.repayment;

import api.BaseAPI;
import io.qameta.allure.Step;
import constants.Uri;
import pojos.lazypay.juspay.repaymentFlow.FindUserOTPPojo;

import java.util.HashMap;
import java.util.Map;

public class FindUserOTP extends BaseAPI<FindUserOTPPojo> {
    public FindUserOTP() throws Exception {
        super(Uri.OTP, FindUserOTPPojo.class);
    }

    @Step
    public FindUserOTPPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}