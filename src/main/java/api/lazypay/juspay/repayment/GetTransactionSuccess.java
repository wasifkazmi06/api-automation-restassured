package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.GetTransactionSuccessPojo;

import java.util.HashMap;
import java.util.Map;

public class GetTransactionSuccess extends BaseAPI<GetTransactionSuccessPojo> {

    public GetTransactionSuccess() throws Exception {
        super(Uri.TRANSACTION_SUCCESS, GetTransactionSuccessPojo.class);
    }

    @Step
    public GetTransactionSuccessPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails );
    }


}



