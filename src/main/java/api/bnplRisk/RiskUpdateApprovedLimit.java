package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskUpdateApprovedLimitPOJO;
import java.util.HashMap;
import java.util.Map;

public class RiskUpdateApprovedLimit extends BaseAPI<RiskUpdateApprovedLimitPOJO> {
    public RiskUpdateApprovedLimit() throws Exception {
            super(Uri.RISK_UPDATE_APPROVED_LIMIT, RiskUpdateApprovedLimitPOJO.class);
    }

    @Step
    public RiskUpdateApprovedLimitPOJO post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
