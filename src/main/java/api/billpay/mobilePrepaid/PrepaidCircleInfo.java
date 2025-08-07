package api.billpay.mobilePrepaid;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.billpayment.BillerDetailsPojo;
import pojos.billpayment.mobilePrepaid.PrepaidcircleInfoPojo;

import java.util.HashMap;
import java.util.Map;

public class PrepaidCircleInfo extends BaseAPI<PrepaidcircleInfoPojo> {

    public PrepaidCircleInfo() throws Exception {
        super(Uri.GET_PREPAIDCIRCLEINFO,PrepaidcircleInfoPojo.class);

    }
    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails,headerDetails);
    }
}
