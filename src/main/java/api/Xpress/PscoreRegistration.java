package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.PscoreRegistrationResponsePojo;

import java.util.HashMap;

public class PscoreRegistration extends BaseAPI<PscoreRegistrationResponsePojo> {

    public PscoreRegistration() throws Exception {
        super(Uri.PSCORE_REGISTRATION_ENDPOINT, PscoreRegistrationResponsePojo.class);
    }

    @Step
    public Response postWithResponse(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.postWithResponse(headerDetails, jsonRequestBody);
    }
}
