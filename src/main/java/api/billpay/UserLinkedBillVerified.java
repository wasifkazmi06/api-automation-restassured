package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.UserLinkedBillVerifiedPojo;
import pojos.billpayment.billFetch.BillFetchPojo;

import java.util.HashMap;
import java.util.Map;

public class UserLinkedBillVerified extends BaseAPI<UserLinkedBillVerifiedPojo> {

    public UserLinkedBillVerified() throws Exception {
        super(Uri.GET_UserLinkedBillId,UserLinkedBillVerifiedPojo.class);

    }
    @Step
    public UserLinkedBillVerifiedPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}