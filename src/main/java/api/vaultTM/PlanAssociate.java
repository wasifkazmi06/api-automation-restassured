package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.PlanAssociatePojo;
import pojos.vaultTM.PlanDisassociatePojo;

import java.util.HashMap;
import java.util.Map;

public class PlanAssociate extends BaseAPI<PlanAssociatePojo> {
    public PlanAssociate() throws Exception {
        super(Uri.PLAN_UPDATE, PlanAssociatePojo.class);
    }

    @Step
    public PlanAssociatePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String requestBody) {
        return super.post(queryParamDetails, headerDetails, requestBody);
    }
}
