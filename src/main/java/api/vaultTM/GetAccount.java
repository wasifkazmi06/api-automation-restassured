package api.vaultTM;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.vaultTM.GetAccountPojo;
import java.util.HashMap;
import java.util.Map;

public class GetAccount extends BaseAPI<GetAccountPojo> {
    public GetAccount() throws Exception {
        super(Uri.GET_ACCOUNT, GetAccountPojo.class);
    }
    @Step
    public GetAccountPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}
