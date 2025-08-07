package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.UpdateRepayStatusPojo;
import java.util.HashMap;
import java.util.Map;

public class UpdateRepayStatus extends BaseAPI<UpdateRepayStatusPojo> {


    public UpdateRepayStatus() throws Exception {
        super(Uri.UPDATE_REPAY_STATUS, UpdateRepayStatusPojo.class);
    }

    @Step
    public UpdateRepayStatusPojo put(Map<String, Object> queryParamDetails,HashMap<String, String> headerDetails, String request) {
        return super.put(queryParamDetails, headerDetails, request);
    }
}
