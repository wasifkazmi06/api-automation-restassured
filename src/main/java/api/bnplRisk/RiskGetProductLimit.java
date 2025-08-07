package api.bnplRisk;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.bnplRisk.RiskGetProductLimitPojo;
import java.util.HashMap;
import java.util.Map;

public class RiskGetProductLimit extends BaseAPI<RiskGetProductLimitPojo> {
    public RiskGetProductLimit() throws Exception {
        super(Uri.RISK_GET_PRODUCT_LIMIT, RiskGetProductLimitPojo.class);
    }
    @Step
    public RiskGetProductLimitPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}

