package api.billpay.mobilePrepaid;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import pojos.billpayment.BillersPojo;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorCircleForMobilePojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidOperatorCircleForMobile extends BaseAPI<PrepaidOperatorCircleForMobilePojo> {

    public PrepaidOperatorCircleForMobile() throws Exception {
        super(Uri.GET_prepaidOperatorCircleForMobile, PrepaidOperatorCircleForMobilePojo.class);

    }
    @Step
    public PrepaidOperatorCircleForMobilePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
