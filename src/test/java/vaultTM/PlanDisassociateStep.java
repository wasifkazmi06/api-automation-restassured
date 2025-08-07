package vaultTM;

import api.vaultTM.PlanDisassociate;
import constants.UtilConstants;
import pojos.vaultTM.PlanDisassociatePojo;
import util.ReadProperties;
import util.StringTemplate;
import java.util.*;

public class PlanDisassociateStep {

    public static PlanDisassociatePojo planDisassociatePojo = new PlanDisassociatePojo();
    public static PlanDisassociate planDisassociate;

    static {
        try {
            planDisassociate = new PlanDisassociate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void planDisassociateMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("X-Auth-Token", ReadProperties.testConfigMap.get(UtilConstants.VAULT_AUTH_TOKEN).toString());

        String planDisassociateRequest = new StringTemplate(vaultTMConstants.PLAN_DISASSOCIATE)
                .replace("request_id", UUID.randomUUID().toString())
                .replace("plan_id", finalRequest.get("plan_id"))
                .replace("account_plan_assoc_id", finalRequest.get("account_plan_assoc_id"))
                .toString();

        planDisassociatePojo = planDisassociate.post(queryParamDetails, headerDetails, planDisassociateRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("plan_id", request.get("plan_id"));
        request.put("account_plan_assoc_id", request.get("account_plan_assoc_id"));

        List keys = new ArrayList(request.keySet());

        for (int i = 0; i < keys.size(); i++) {
            if (request.get(keys.get(i)) == null) {
                request.put(keys.get(i).toString(), "");
            }
        }

        return request;
    }
}
