package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class FetchPanDetails extends BaseAPI<Response> {

    public FetchPanDetails() throws Exception {
        super(Uri.FETCH_PAN_DETAILS, Response.class);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return  super.getWithResponse(queryParamDetails,headerDetails);
    }
}
