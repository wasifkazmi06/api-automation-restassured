package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.PlanMigratePojo;
import java.util.HashMap;
import java.util.Map;

public class PlanMigrate extends BaseAPI<PlanMigratePojo> {
    public PlanMigrate() throws Exception {
        super(Uri.PLAN_UPDATE, PlanMigratePojo.class);
    }

    @Step
    public PlanMigratePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String requestBody) {
        return super.post(queryParamDetails, headerDetails, requestBody);
    }
}
