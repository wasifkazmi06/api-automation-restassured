package api.userRegistration;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.ProfileData;

import java.util.HashMap;
import java.util.Map;

public class FindUser extends BaseAPI<ProfileData> {
    public FindUser() throws Exception {
        super(Uri.URS_FIND_USER,ProfileData.class);
    }

    @Step
    public Response postWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(queryParamDetails, headerDetails, jsonRequestBody);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }

}
