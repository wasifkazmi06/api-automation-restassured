package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.getTransactionHistory.GetAppTransactionhistory;
import pojos.neobank.support.preference.cardStatus;

import java.util.HashMap;
import java.util.Map;

public class GetApptransactionHistory extends BaseAPI<GetAppTransactionhistory> {
    public GetApptransactionHistory() throws Exception {
        super(Uri.GET_APP_TRANSACTION_HISTORY, GetAppTransactionhistory.class);
    }
    @Step
    public GetAppTransactionhistory get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, HashMap<String, String> path){
        return super.get(queryParamDetails, headerDetails,path);
    }
}
