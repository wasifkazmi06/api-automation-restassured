package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.GetSupportedBankListPojo;
import java.util.HashMap;
import java.util.Map;

public class GetSupportedBankList extends BaseAPI<GetSupportedBankListPojo> {
    public GetSupportedBankList() throws Exception {
            super(Uri.GET_SUPPORTED_BANK_LIST, GetSupportedBankListPojo.class);
    }
    @Step
    public GetSupportedBankListPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
