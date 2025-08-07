package api.upi.bnpl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.bnpl.ValidateTransactionPojo;
import java.util.HashMap;
import java.util.Map;

public class ValidateTransaction extends BaseAPI<ValidateTransactionPojo> {

    public ValidateTransaction() throws Exception {
        super(Uri.VALIDATE_TXN_UPI, ValidateTransactionPojo.class);
    }
    @Step
    public ValidateTransactionPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
