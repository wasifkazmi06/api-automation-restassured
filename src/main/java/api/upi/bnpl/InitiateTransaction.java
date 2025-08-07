package api.upi.bnpl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.bnpl.InitiateTransactionPojo;

import java.util.HashMap;
import java.util.Map;

public class InitiateTransaction extends BaseAPI<InitiateTransactionPojo> {

    public InitiateTransaction() throws Exception {
        super(Uri.INITIATE_TXN_UPI, InitiateTransactionPojo.class);
    }
    @Step
    public InitiateTransactionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
