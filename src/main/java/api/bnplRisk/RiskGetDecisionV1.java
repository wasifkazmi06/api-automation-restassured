package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskGetDecisionPojo;
import java.util.HashMap;
import java.util.Map;

public class RiskGetDecisionV1 extends BaseAPI<RiskGetDecisionPojo> {
    public RiskGetDecisionV1() throws Exception {
            super(Uri.RISK_GET_DECISION_V1, RiskGetDecisionPojo.class);
    }

    @Step
    public RiskGetDecisionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
