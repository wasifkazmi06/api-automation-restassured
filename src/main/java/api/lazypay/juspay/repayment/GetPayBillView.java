package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.GetPayBillViewPojo;


import java.util.HashMap;
import java.util.Map;

public class GetPayBillView extends BaseAPI<GetPayBillViewPojo> {
    public GetPayBillView() throws Exception {
        super(Uri.PAY_BILL, GetPayBillViewPojo.class);
    }

    @Step
    public GetPayBillViewPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails );
    }


}
