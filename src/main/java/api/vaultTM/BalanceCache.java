package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.BalanceCachePojo;
import java.util.HashMap;
import java.util.Map;

public class BalanceCache extends BaseAPI<BalanceCachePojo> {
        public BalanceCache() throws Exception {
            super(Uri.BALANCE_CACHE, BalanceCachePojo.class);
        }
        @Step
        public BalanceCachePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
            return super.get(queryParamDetails, headerDetails, pathParam);
        }
}
