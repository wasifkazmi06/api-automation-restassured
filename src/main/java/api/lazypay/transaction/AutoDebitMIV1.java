package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.AutoDebitPayMIPojo;
import pojos.lazypay.transactionFlow.PayPojo;

import java.util.HashMap;
import java.util.Map;

public class AutoDebitMIV1 extends BaseAPI<AutoDebitPayMIPojo> {
    public AutoDebitMIV1() throws Exception {
        super(Uri.AUTODEBITMIV1, AutoDebitPayMIPojo.class);
    }
    @Step
    public AutoDebitPayMIPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails,headerDetails,request);
    }
}

