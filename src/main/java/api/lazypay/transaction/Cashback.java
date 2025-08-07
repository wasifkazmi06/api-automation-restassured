package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.CashbackPojo;

import java.util.HashMap;

public class Cashback extends BaseAPI<CashbackPojo> {
    public Cashback() throws Exception {
        super(Uri.CASHBACK, CashbackPojo.class);
    }

    @Step
    public CashbackPojo post(HashMap<String, String> headerDetails, String request) {
        return super.post(headerDetails,request);
    }


}
