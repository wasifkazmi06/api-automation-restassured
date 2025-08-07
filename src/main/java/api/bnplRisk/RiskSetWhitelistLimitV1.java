package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskSetWhitelistLimitV1Pojo;
import java.util.HashMap;
import java.util.Map;

public class RiskSetWhitelistLimitV1 extends BaseAPI<RiskSetWhitelistLimitV1Pojo> {
    public RiskSetWhitelistLimitV1() throws Exception {
        super(Uri.RISK_SET_WHITELIST_LIMIT_V1, RiskSetWhitelistLimitV1Pojo.class);
    }
    @Step
    public RiskSetWhitelistLimitV1Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}

