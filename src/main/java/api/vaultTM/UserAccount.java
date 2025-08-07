package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.UserAccountPojo;
import java.util.HashMap;
import java.util.Map;

public class UserAccount extends BaseAPI<UserAccountPojo> {
        public UserAccount() throws Exception {
            super(Uri.BALANCE_LEDGER, UserAccountPojo.class);
        }
        @Step
        public UserAccountPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
            return super.get(queryParamDetails, headerDetails, pathParam);
        }
}
