package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskPLSetWhitelistLimitPojo;
import java.util.HashMap;
import java.util.Map;

public class RiskPLSetWhitelistLimit extends BaseAPI<RiskPLSetWhitelistLimitPojo> {
    public RiskPLSetWhitelistLimit() throws Exception {
        super(Uri.RISK_PL_SET_WHITELIST_LIMIT, RiskPLSetWhitelistLimitPojo.class);
    }
    @Step
    public RiskPLSetWhitelistLimitPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}

