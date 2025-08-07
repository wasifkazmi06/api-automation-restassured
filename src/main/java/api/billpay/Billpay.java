package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.billFetch.BillFetchPojo;
import pojos.billpayment.billpay.BillpayPojo;

import java.util.HashMap;
import java.util.Map;

public class Billpay extends BaseAPI<BillpayPojo> {

    public Billpay() throws Exception {
        super(Uri.BILL_PAY,BillpayPojo.class);

    }
    @Step
    public BillpayPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}