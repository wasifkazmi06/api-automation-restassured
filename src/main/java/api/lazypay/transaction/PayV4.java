package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.PayPojo;

import java.util.HashMap;
import java.util.Map;

public class PayV4 extends BaseAPI<PayPojo> {
    public PayV4() throws Exception {
        super(Uri.PAYV4, PayPojo.class);
    }
    @Step
    public PayPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}

