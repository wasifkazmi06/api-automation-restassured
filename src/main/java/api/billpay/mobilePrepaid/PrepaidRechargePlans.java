package api.billpay.mobilePrepaid;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.billpayment.BillersPojo;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorandCirclePojo;
import pojos.billpayment.mobilePrepaid.PrepaidRechargePlansPojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidRechargePlans extends BaseAPI<PrepaidRechargePlansPojo> {

    public PrepaidRechargePlans() throws Exception {
        super(Uri.GET_prepaidRechargePlans,PrepaidRechargePlansPojo.class);

    }
    @Step
    public PrepaidRechargePlansPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
