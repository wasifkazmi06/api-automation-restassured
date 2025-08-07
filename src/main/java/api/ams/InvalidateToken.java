package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.InvalidateTokenPojo;

import java.util.HashMap;
import java.util.Map;

public class InvalidateToken extends BaseAPI<InvalidateTokenPojo> {


    public InvalidateToken() throws Exception {
        super(Uri.PLATFORM_TOKEN_INVALIDATE, InvalidateTokenPojo.class);
    }

    @Step
    public InvalidateTokenPojo delete(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.delete(queryParamDetails, headerDetails);
    }

    @Step
    public Response deleteWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.deleteWithResponse(queryParamDetails, headerDetails);
    }
}
