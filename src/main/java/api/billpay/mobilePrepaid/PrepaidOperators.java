package api.billpay.mobilePrepaid;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorPojo;
import pojos.billpayment.mobilePrepaid.PrepaidcircleInfoPojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidOperators extends BaseAPI<PrepaidOperatorPojo> {

    public PrepaidOperators() throws Exception {
        super(Uri.GET_PREPAIDOPERATOR,PrepaidOperatorPojo.class);

    }
    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails,headerDetails);
    }
}
