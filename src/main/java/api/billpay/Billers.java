package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.BillersPojo;

import java.util.HashMap;
import java.util.Map;

public class Billers extends BaseAPI<BillersPojo> {

    public Billers() throws Exception {
        super(Uri.GET_BILLERS, BillersPojo.class);
    }
    @Step
    public BillersPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}
