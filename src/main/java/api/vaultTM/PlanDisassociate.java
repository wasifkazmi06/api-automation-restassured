package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.PlanDisassociatePojo;
import java.util.HashMap;
import java.util.Map;

public class PlanDisassociate extends BaseAPI<PlanDisassociatePojo> {
    public PlanDisassociate() throws Exception {
        super(Uri.PLAN_UPDATE, PlanDisassociatePojo.class);
    }

    @Step
    public PlanDisassociatePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String requestBody) {
        return super.post(queryParamDetails, headerDetails, requestBody);
    }
}
