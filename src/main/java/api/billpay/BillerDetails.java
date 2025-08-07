package api.billpay;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.billpayment.BillerDetailsPojo;

import java.util.HashMap;
import java.util.Map;

public class BillerDetails extends BaseAPI<BillerDetailsPojo> {

    public BillerDetails() throws Exception {
        super(Uri.GET_BILLERDETAILS,BillerDetailsPojo.class);

    }
    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails,headerDetails);
    }
}


