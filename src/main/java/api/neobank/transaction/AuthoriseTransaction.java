package api.neobank.transaction;

import io.qameta.allure.Step;
import api.BaseAPI;
import constants.Uri;
import pojos.neobank.transaction.AuthorizetransactionPojo;
import java.util.HashMap;
import java.util.Map;

public class AuthoriseTransaction extends BaseAPI<AuthorizetransactionPojo> {
    public AuthoriseTransaction() throws Exception {
        super(Uri.NEO_AUTHORIZE_TRANSACTION, AuthorizetransactionPojo.class);
    }
    @Step
    public AuthorizetransactionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
