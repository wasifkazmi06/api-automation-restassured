package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.PaymentMethodPojo;
import pojos.lazypay.repaymentFlow.SuccessRedirectPojo;

import java.util.HashMap;
import java.util.Map;

public class SuccessRedirect extends BaseAPI<SuccessRedirectPojo> {
    public SuccessRedirect() throws Exception {
        super(Uri.SUCCESSREDIRECT, SuccessRedirectPojo.class);
    }
    @Step
    public SuccessRedirectPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}