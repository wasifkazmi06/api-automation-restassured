package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.RepayRefundPojo;

import java.util.HashMap;

public class RepayRefund extends BaseAPI<RepayRefundPojo> {


    public RepayRefund() throws Exception {
        super(Uri.REPAYREFUND_JP, RepayRefundPojo.class);
    }
    @Step
    public RepayRefundPojo post(HashMap<String, String> headerDetails, String jsonRequestBody)
    {
        return super.post(headerDetails, jsonRequestBody);
    }
}
