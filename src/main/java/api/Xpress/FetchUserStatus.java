package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.FetchUserStatusResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class FetchUserStatus extends BaseAPI<FetchUserStatusResponsePojo> {

    public FetchUserStatus() throws Exception {
        super(Uri.SECURE_V0_STATUS_FETCH, FetchUserStatusResponsePojo.class);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }
}
