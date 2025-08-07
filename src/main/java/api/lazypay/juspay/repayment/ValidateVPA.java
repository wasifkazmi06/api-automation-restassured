package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.ValidateVPAPojo;
import java.util.HashMap;
import java.util.Map;

public class ValidateVPA extends BaseAPI<ValidateVPAPojo> {
    public ValidateVPA() throws Exception {
        super(Uri.VALIDATEVPA_JP, ValidateVPAPojo.class);
    }
    @Step
    public ValidateVPAPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}