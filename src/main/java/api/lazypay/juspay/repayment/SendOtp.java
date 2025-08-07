package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.SendOtpPojo;

import java.util.HashMap;
import java.util.Map;

public class SendOtp extends BaseAPI<SendOtpPojo> {
    public SendOtp() throws Exception {
        super(Uri.SEND_OTP_MBE, SendOtpPojo.class);
    }

    @Step
    public SendOtpPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }


}
