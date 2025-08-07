package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.BankVerifyResponsePojo;

import java.util.HashMap;

public class VerifyBankAccount extends BaseAPI<BankVerifyResponsePojo> {

    public VerifyBankAccount() throws Exception {
        super(Uri.VERIFY_BANK_ACCOUNT_SHYLOCK, BankVerifyResponsePojo.class);
    }

    @Step
    public Response postWithResponse(String jsonBody, HashMap<String, String> headerDetails) {
        return super.postWithResponse(headerDetails, jsonBody);
    }
}
