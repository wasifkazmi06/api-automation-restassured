package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLUpdatePreApproveLimitPojo;
import java.util.HashMap;
import java.util.Map;

public class RiskPLUpdatePreApproveLimit extends BaseAPI<RiskPLUpdatePreApproveLimitPojo> {
    public RiskPLUpdatePreApproveLimit() throws Exception {
        super(Uri.RISK_PL_UPDATE_PRE_APPROVED_LIMIT, RiskPLUpdatePreApproveLimitPojo.class);
    }
    @Step
    public RiskPLUpdatePreApproveLimitPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}

