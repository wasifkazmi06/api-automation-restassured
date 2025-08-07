package api.ams;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.ams.ValidateTokenPojo;

import java.util.HashMap;
import java.util.Map;

public class TokenValidate extends BaseAPI<ValidateTokenPojo> {

    public TokenValidate() throws Exception {
        super(Uri.PLATFORM_TOKEN_VALIDATE, ValidateTokenPojo.class);
    }

    @Step
    public ValidateTokenPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }

    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }
}
