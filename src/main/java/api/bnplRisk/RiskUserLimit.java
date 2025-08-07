package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.bnplRisk.RiskUserLimitPOJO;
import java.util.HashMap;
import java.util.Map;

public class RiskUserLimit extends BaseAPI<RiskUserLimitPOJO> {
    public RiskUserLimit() throws Exception {
        super(Uri.RISK_USER_LIMIT, RiskUserLimitPOJO.class);
    }
    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.postWithResponse(queryParamDetails, headerDetails, request);
    }

    @Step
    public RiskUserLimitPOJO post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}

