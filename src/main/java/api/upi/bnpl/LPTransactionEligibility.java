package api.upi.bnpl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.bnpl.LPTransactionEligibilityPojo;

import java.util.HashMap;
import java.util.Map;

public class LPTransactionEligibility extends BaseAPI<LPTransactionEligibilityPojo> {

    public LPTransactionEligibility() throws Exception {
        super(Uri.LP_UPI_TXN_ELIGIBILITY, LPTransactionEligibilityPojo.class);
    }
    @Step
    public LPTransactionEligibilityPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
