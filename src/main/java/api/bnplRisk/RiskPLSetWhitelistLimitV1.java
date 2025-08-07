package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLSetWhitelistLimitV1Pojo;
import java.util.HashMap;
import java.util.Map;

public class RiskPLSetWhitelistLimitV1 extends BaseAPI<RiskPLSetWhitelistLimitV1Pojo> {
    public RiskPLSetWhitelistLimitV1() throws Exception {
        super(Uri.RISK_PL_SET_WHITELIST_LIMIT_V1, RiskPLSetWhitelistLimitV1Pojo.class);
    }
    @Step
    public RiskPLSetWhitelistLimitV1Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}

