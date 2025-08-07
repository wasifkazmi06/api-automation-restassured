package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.PaymentStatusPojo;

import java.util.HashMap;
import java.util.Map;

public class BillPaymentStatus extends BaseAPI<PaymentStatusPojo> {

    public BillPaymentStatus() throws Exception {
        super(Uri.GET_PAYMENTSTATUS, PaymentStatusPojo.class);
    }
    @Step
    public PaymentStatusPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}
