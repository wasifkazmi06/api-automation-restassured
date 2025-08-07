package api.neobank.cardControl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.cardControl.SetTransactionLimitPojo;
import pojos.neobank.support.preference.cardStatus;
import pojos.neobank.transaction.AuthorizetransactionPojo;

import java.util.HashMap;
import java.util.Map;

public class SetTransactionLimit extends BaseAPI<SetTransactionLimitPojo> {
    public SetTransactionLimit() throws Exception {
        super(Uri.NEO_CARD_CONTROL_SETLIMIT, SetTransactionLimitPojo.class);
    }
    @Step
    public SetTransactionLimitPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
