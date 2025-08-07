package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.FraudGetDecisionPojo;
import java.util.HashMap;
import java.util.Map;

public class FraudGetDecision extends BaseAPI<FraudGetDecisionPojo> {
    public FraudGetDecision() throws Exception {
            super(Uri.FRAUD_GET_DECISION, FraudGetDecisionPojo.class);
    }

    @Step
    public FraudGetDecisionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
