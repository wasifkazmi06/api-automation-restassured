package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskLimitPostMITCPOJO;
import java.util.HashMap;
import java.util.Map;

public class RiskLimitPostMITC extends BaseAPI<RiskLimitPostMITCPOJO> {
    public RiskLimitPostMITC() throws Exception {
            super(Uri.RISK_UPDATE_APPROVED_LIMIT, RiskLimitPostMITCPOJO.class);
    }

    @Step
    public RiskLimitPostMITCPOJO post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
