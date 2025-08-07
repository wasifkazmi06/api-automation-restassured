package api.platform;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.platform.UpdateUserStatusPojo;
import java.util.HashMap;
import java.util.Map;

public class UpdateUserStatus extends BaseAPI<UpdateUserStatusPojo> {
    public UpdateUserStatus() throws Exception {
        super(Uri.UPDATE_USER_STATUS, UpdateUserStatusPojo.class);
    }
    @Step
    public Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.postReturnResponse(queryParamDetails, headerDetails, request);
    }
}

