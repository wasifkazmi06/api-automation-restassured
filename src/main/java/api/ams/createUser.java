package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.validateUserPojo;

import java.util.HashMap;
import java.util.Map;


public class createUser extends BaseAPI<validateUserPojo> {
    public createUser() throws Exception {
        super(Uri.FIND_OR_CREATE_USER, validateUserPojo.class);
    }

    @Step
    public validateUserPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
