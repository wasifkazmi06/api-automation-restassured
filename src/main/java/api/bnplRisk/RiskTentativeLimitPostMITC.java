package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskTentativeLimitPostMITCPOJO;
import java.util.HashMap;
import java.util.Map;

public class RiskTentativeLimitPostMITC extends BaseAPI<RiskTentativeLimitPostMITCPOJO> {
    public RiskTentativeLimitPostMITC() throws Exception {
        super(Uri.RISK_TENTATIVE_PRODUCT_LIMIT_POST_MITC, RiskTentativeLimitPostMITCPOJO.class);
    }
    @Step
    public RiskTentativeLimitPostMITCPOJO get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}

