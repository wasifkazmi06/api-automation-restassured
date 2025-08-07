package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.InternalAccountPojo;
import java.util.HashMap;
import java.util.Map;

public class InternalAccount extends BaseAPI<InternalAccountPojo> {
    public InternalAccount() throws Exception {
        super(Uri.VAULT_INTERNAL_ACCOUNT, InternalAccountPojo.class);
    }
    @Step
    public InternalAccountPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}
