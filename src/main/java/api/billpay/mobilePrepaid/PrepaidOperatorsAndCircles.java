package api.billpay.mobilePrepaid;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorPojo;
import pojos.billpayment.mobilePrepaid.PrepaidOperatorandCirclePojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidOperatorsAndCircles extends BaseAPI<PrepaidOperatorandCirclePojo> {

    public PrepaidOperatorsAndCircles() throws Exception {
        super(Uri.GET_prepaidOperatorsAndCircles,PrepaidOperatorandCirclePojo.class);

    }
    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails,headerDetails);
    }
}
