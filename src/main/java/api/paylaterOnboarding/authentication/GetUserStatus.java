package api.paylaterOnboarding.authentication;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.paylaterOnboarding.PLGetUserStatusPojo;

import java.util.HashMap;
import java.util.Map;

public class GetUserStatus extends BaseAPI<PLGetUserStatusPojo> {
    public GetUserStatus() throws Exception {
        super(Uri.PL_USER_STATUS, PLGetUserStatusPojo.class);
    }

    @Step
    public Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithPathPram(queryParamDetails,headerDetails,"");
    }
}
