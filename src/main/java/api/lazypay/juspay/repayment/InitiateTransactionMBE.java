package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.InitiateTransactionMBEPojo;

import java.util.HashMap;

public class InitiateTransactionMBE extends BaseAPI<InitiateTransactionMBEPojo> {

    public InitiateTransactionMBE() throws Exception {
        super(Uri.MBE_INIIATE_TRANSATION, InitiateTransactionMBEPojo.class);
    }
    @Step
    public InitiateTransactionMBEPojo post(HashMap<String, String> headerDetails, String jsonRequestBody)
    {
        return super.post(headerDetails, jsonRequestBody);
    }

}
