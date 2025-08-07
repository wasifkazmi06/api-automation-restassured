package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.GetTransactionStatusPojo;
import pojos.lazypay.juspay.repaymentFlow.InitiateTransactionMBEPojo;

import java.util.HashMap;
import java.util.Map;

public class GetTransactionStatus extends BaseAPI<GetTransactionStatusPojo> {

    public GetTransactionStatus() throws Exception {
        super(Uri.TRANSACTION_STATUS, GetTransactionStatusPojo.class);
    }
    @Step
    public GetTransactionStatusPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody)
    {
        return super.post(queryParamDetails,headerDetails, jsonRequestBody);
    }

}
