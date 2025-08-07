package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.AccountUpdatePojo;
import java.util.HashMap;
import java.util.Map;

    public class AccountUpdate extends BaseAPI<AccountUpdatePojo> {
        public AccountUpdate() throws Exception {
            super(Uri.ACCOUNT_UPDATE, AccountUpdatePojo.class);
        }

        @Step
        public AccountUpdatePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String requestBody) {
            return super.post(queryParamDetails, headerDetails, requestBody);
        }


}
