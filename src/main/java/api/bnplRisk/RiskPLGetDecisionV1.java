package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLGetDecisionPojo;
import java.util.HashMap;
import java.util.Map;

public class RiskPLGetDecisionV1 extends BaseAPI<RiskPLGetDecisionPojo> {
    public RiskPLGetDecisionV1() throws Exception {
        super(Uri.RISK_PL_GET_DECISION_V1, RiskPLGetDecisionPojo.class);
    }
    @Step
    public RiskPLGetDecisionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}

