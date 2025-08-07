package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.BillersPojo;
import pojos.billpayment.UpcomingbillPojo;

import java.util.HashMap;
import java.util.Map;

public class Upcomingbills extends BaseAPI<UpcomingbillPojo> {

    public Upcomingbills() throws Exception {
        super(Uri.GET_UPCOMINGBILLS, UpcomingbillPojo.class);
    }
    @Step
    public UpcomingbillPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

}