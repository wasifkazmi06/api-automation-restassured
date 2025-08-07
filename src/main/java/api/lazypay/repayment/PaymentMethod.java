package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.PaymentMethodPojo;
import pojos.lazypay.repaymentFlow.PreferredMethodPojo;

import java.util.HashMap;
import java.util.Map;

public class PaymentMethod extends BaseAPI<PaymentMethodPojo> {
    public PaymentMethod() throws Exception {
        super(Uri.PAYMENTMETHOD, PaymentMethodPojo.class);
    }
    @Step
    public PaymentMethodPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}