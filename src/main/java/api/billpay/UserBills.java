package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.UpcomingbillPojo;
import pojos.billpayment.UserbillsPojo;

import java.util.HashMap;
import java.util.Map;

public class UserBills extends BaseAPI<UserbillsPojo> {

    public UserBills() throws Exception {
        super(Uri.GET_USERSBILLS, UserbillsPojo.class);
    }
    @Step
    public UserbillsPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
