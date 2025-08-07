package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.billFetch.BillFetchPojo;

import java.util.HashMap;
import java.util.Map;

public class BillFetch extends BaseAPI<BillFetchPojo> {

   public BillFetch() throws Exception {
       super(Uri.BILL_FETCH,BillFetchPojo.class);

   }
    @Step
    public BillFetchPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
