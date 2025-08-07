package api.upi.convertToEMI;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.convertToEMI.EMITransactionEligibilityPojo;
import java.util.HashMap;
import java.util.Map;

public class EMITransactionEligibility extends BaseAPI<EMITransactionEligibilityPojo> {

    public EMITransactionEligibility() throws Exception {
        super(Uri.CL_UPI_TXN_ELIGIBILITY, EMITransactionEligibilityPojo.class);
    }
    @Step
    public EMITransactionEligibilityPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
