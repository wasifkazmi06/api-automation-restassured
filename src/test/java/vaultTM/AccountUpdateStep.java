package vaultTM;

import api.vaultTM.AccountUpdate;
import constants.UtilConstants;
import pojos.vaultTM.AccountUpdatePojo;
import util.ReadProperties;
import util.StringTemplate;
import java.util.*;

public class AccountUpdateStep {

    public static AccountUpdatePojo accountUpdatePojo = new AccountUpdatePojo();
    public static AccountUpdate accountUpdate;

    static {
        try {
            accountUpdate = new AccountUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void accountUpdateMethod(HashMap<String, String> request){

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        HashMap<String, String> finalRequest = requestBuilder(request);

        headerDetails.put("X-Auth-Token",  ReadProperties.testConfigMap.get(UtilConstants.VAULT_AUTH_TOKEN).toString());

        String planDisassociateRequest = new StringTemplate(vaultTMConstants.ACCOUNT_UPDATE)
                .replace("request_id", UUID.randomUUID().toString())
                .replace("account_id", finalRequest.get("account_id"))
                .replace("product_version_id", finalRequest.get("product_version_id"))
                .replace("schedule_migration_type", finalRequest.get("schedule_migration_type"))
                .toString();

        accountUpdatePojo = accountUpdate.post(queryParamDetails, headerDetails, planDisassociateRequest);
    }

    private static HashMap requestBuilder(HashMap<String, String> request) {

        request.put("account_id", request.get("account_id"));
        request.put("product_version_id", request.get("product_version_id"));
        request.put("schedule_migration_type", request.get("schedule_migration_type"));


        List keys = new ArrayList(request.keySet());

        for (int i = 0; i < keys.size(); i++) {
            if (request.get(keys.get(i)) == null) {
                request.put(keys.get(i).toString(), "");
            }
        }

        if (request.get("schedule_migration_type").equals("")){
            request.put("schedule_migration_type", VaultTMData.scheduleMigrationType);
        }

        return request;
    }
}
