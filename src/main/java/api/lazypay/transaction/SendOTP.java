package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.transactionFlow.SendOTPPojo;
import java.util.HashMap;
import java.util.Map;

public class SendOTP extends BaseAPI<SendOTPPojo> {
    public SendOTP() throws Exception {
            super(Uri.SEND_OTP, SendOTPPojo.class);
    }

    @Step
    public Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.postReturnResponse(queryParamDetails, headerDetails, request);
    }
}
