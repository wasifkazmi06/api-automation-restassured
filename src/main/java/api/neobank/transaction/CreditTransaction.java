package api.neobank.transaction;

import io.qameta.allure.Step;
import api.BaseAPI;
import constants.Uri;
import pojos.neobank.transaction.CreditApiPojo;

import java.util.HashMap;
import java.util.Map;

public class CreditTransaction extends BaseAPI<CreditApiPojo> {
    public CreditTransaction() throws Exception {
        super(Uri.NEO_CREDIT_TRANSACTION, CreditApiPojo.class);
    }
    @Step
    public CreditApiPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
