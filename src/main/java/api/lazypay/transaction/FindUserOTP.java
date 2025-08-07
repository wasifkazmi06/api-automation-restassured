package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.FindUserOTPPojo;
import pojos.lazypay.transactionFlow.PayPojo;

import java.util.HashMap;
import java.util.Map;

public class FindUserOTP extends BaseAPI<FindUserOTPPojo> {
    public FindUserOTP() throws Exception {
        super(Uri.OTP, FindUserOTPPojo.class);
    }
    @Step
    public FindUserOTPPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}

