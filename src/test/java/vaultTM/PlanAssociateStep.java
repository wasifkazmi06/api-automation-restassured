package vaultTM;

import api.vaultTM.PlanAssociate;
import constants.UtilConstants;
import pojos.vaultTM.PlanAssociatePojo;
import util.ReadProperties;
import util.StringTemplate;
import java.util.*;

public class PlanAssociateStep {

    public static PlanAssociatePojo planAssociatePojo = new PlanAssociatePojo();
    public static PlanAssociate planAssociate;

    static {
        try {
            planAssociate = new PlanAssociate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void planAssociateMethod(HashMap<String, String> request) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("X-Auth-Token", ReadProperties.testConfigMap.get(UtilConstants.VAULT_AUTH_TOKEN).toString());

        String planDisassociateRequest = new StringTemplate(vaultTMConstants.PLAN_ASSOCIATE)
                .replace("request_id", UUID.randomUUID().toString())
                .replace("plan_id", finalRequest.get("plan_id"))
                .replace("account_id", finalRequest.get("account_id"))
                .toString();

        planAssociatePojo = planAssociate.post(queryParamDetails, headerDetails, planDisassociateRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("plan_id", request.get("plan_id"));
        request.put("account_id", request.get("account_id"));

        List keys = new ArrayList(request.keySet());

        for (int i = 0; i < keys.size(); i++) {
            if (request.get(keys.get(i)) == null) {
                request.put(keys.get(i).toString(), "");
            }
        }

        return request;
    }
}
