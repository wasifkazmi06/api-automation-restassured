package api.nach.presentment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.nach.InitiateIRSPojo;
import pojos.nach.presentment.InitiateRazorpayWebhookPojo;
import pojos.neobank.transaction.AuthorizetransactionPojo;

import java.util.HashMap;
import java.util.Map;

public class InitiateRazorpayWebhook extends BaseAPI<InitiateRazorpayWebhookPojo> {
    public InitiateRazorpayWebhook() throws Exception {
            super(Uri.INITIATE_WEBHOOK, InitiateRazorpayWebhookPojo.class);
    }
    @Step
    public Response post1(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post1(queryParamDetails, headerDetails,request);
    }
}


