package api.platform;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.platform.getUserData.UserData;

import java.util.HashMap;
import java.util.Map;

public class GetUserData extends BaseAPI<UserData> {

    public GetUserData() throws Exception {
        super(Uri.PLATFORM_GET_USER, UserData.class);
    }

    @Step
    public UserData get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }
}

