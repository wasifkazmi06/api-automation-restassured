package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;

import pojos.lazypay.juspay.repaymentFlow.GetPaymentModesViewPojo;
import java.util.HashMap;
import java.util.Map;

public class GetPaymentModesView extends BaseAPI<GetPaymentModesViewPojo> {

    public GetPaymentModesView() throws Exception {
        super(Uri.PAYMENT_MODES, GetPaymentModesViewPojo.class);
    }

        @Step
        public GetPaymentModesViewPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails );
    }
}
