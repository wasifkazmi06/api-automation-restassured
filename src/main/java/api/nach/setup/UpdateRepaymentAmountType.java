package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.UpdateRepaymentAmountTypePojo;
import java.util.HashMap;
import java.util.Map;

public class UpdateRepaymentAmountType extends BaseAPI<UpdateRepaymentAmountTypePojo> {
    public UpdateRepaymentAmountType() throws Exception {
            super(Uri.UPDATE_REPAYMENT_AMOUNT_TYPE, UpdateRepaymentAmountTypePojo.class);
    }
    @Step
    public UpdateRepaymentAmountTypePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}
