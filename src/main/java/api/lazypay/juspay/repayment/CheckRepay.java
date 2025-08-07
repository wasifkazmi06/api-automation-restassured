package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.CheckRepayPojo;

import java.util.HashMap;
import java.util.Map;

public class CheckRepay extends BaseAPI<CheckRepayPojo> {


    public CheckRepay() throws Exception {
        super(Uri.CHECKREPAY_JP, CheckRepayPojo.class);
    }

    @Step
    public CheckRepayPojo get(Map<String, Object> queryParamDetails,HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails,headerDetails);
    }
}
