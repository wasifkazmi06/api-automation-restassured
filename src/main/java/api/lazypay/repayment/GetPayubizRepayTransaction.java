package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.GetPayubizRepayTransactionPojo;
import java.util.HashMap;
import java.util.Map;

public class GetPayubizRepayTransaction extends BaseAPI<GetPayubizRepayTransactionPojo> {
    public GetPayubizRepayTransaction() throws Exception {
        super(Uri.GETPAYUBIZREPAYTRANSACTION, GetPayubizRepayTransactionPojo.class);
    }
    @Step
    public GetPayubizRepayTransactionPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}