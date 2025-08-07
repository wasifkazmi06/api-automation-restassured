package api.neobank.cardControl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.cardControl.GetUserTransactionLimitPojo;

import java.util.HashMap;
import java.util.Map;

public class GetTrnsactionLimit extends BaseAPI<GetUserTransactionLimitPojo> {
    public GetTrnsactionLimit() throws Exception {
        super(Uri.NEO_CARD_CONTROL_GETLIMIT, GetUserTransactionLimitPojo.class);
    }
    @Step
    public GetUserTransactionLimitPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String path){
        return super.get(queryParamDetails, headerDetails,path);
    }
}
