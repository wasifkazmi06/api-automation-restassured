package api.neobank.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.neobank.transaction.AuthorizetransactionPojo;

import java.util.HashMap;
import java.util.Map;

public class Webhook extends BaseAPI<AuthorizetransactionPojo> {
    public Webhook() throws Exception {
        super(Uri.YAP_CALLBACK_REQ, AuthorizetransactionPojo.class);
    }
    @Step
    public Response post1(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String request) {
        return super.post1(queryParamDetails, headerDetails,request);
    }
}
