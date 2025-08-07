package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.UserbillsPojo;
import pojos.billpayment.fetchBillAndLastBillDetails.UserLinkedBillIdPojo;

import java.util.HashMap;
import java.util.Map;

public class UserLinkedBillId extends BaseAPI<UserLinkedBillIdPojo> {

    public UserLinkedBillId() throws Exception {
        super(Uri.GET_FETCHBILLDETAILS, UserLinkedBillIdPojo.class);
    }
    @Step
    public UserLinkedBillIdPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
