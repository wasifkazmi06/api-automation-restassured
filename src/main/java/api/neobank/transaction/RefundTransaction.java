package api.neobank.transaction;

import io.qameta.allure.Step;
import api.BaseAPI;
import constants.Uri;
import pojos.neobank.transaction.RefundTransactionPojo;

import java.util.HashMap;
import java.util.Map;

public class RefundTransaction extends BaseAPI<RefundTransactionPojo> {
    public RefundTransaction() throws Exception {
        super(Uri.NEO_REFUND_TRANSACTION, RefundTransactionPojo.class);
    }
    @Step
    public RefundTransactionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
