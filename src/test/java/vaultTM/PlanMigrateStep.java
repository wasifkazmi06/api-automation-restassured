package vaultTM;

import api.vaultTM.PlanMigrate;
import constants.UtilConstants;
import pojos.vaultTM.PlanMigratePojo;
import util.ReadProperties;
import util.StringTemplate;
import java.util.*;

public class PlanMigrateStep {

    public static PlanMigratePojo planMigratePojo = new PlanMigratePojo();
    public static PlanMigrate planMigrate;

    static {
        try {
            planMigrate = new PlanMigrate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void planMigrateMethod(HashMap<String, String> request) {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("X-Auth-Token", ReadProperties.testConfigMap.get(UtilConstants.VAULT_AUTH_TOKEN).toString());

        String planDisassociateRequest = new StringTemplate(vaultTMConstants.PLAN_MIGRATE)
                .replace("request_id", UUID.randomUUID().toString())
                .replace("plan_id", finalRequest.get("plan_id"))
                .replace("supervisor_contract_version_id", finalRequest.get("supervisor_contract_version_id"))
                .toString();

        planMigratePojo = planMigrate.post(queryParamDetails, headerDetails, planDisassociateRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("plan_id", request.get("plan_id"));
        request.put("supervisor_contract_version_id", request.get("supervisor_contract_version_id"));

        List keys = new ArrayList(request.keySet());

        for (int i = 0; i < keys.size(); i++) {
            if (request.get(keys.get(i)) == null) {
                request.put(keys.get(i).toString(), "");
            }
        }

        return request;
    }
}
