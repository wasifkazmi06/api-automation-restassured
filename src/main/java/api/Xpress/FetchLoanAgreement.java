package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.FetchLoanAgreementResponsePojo;

import java.util.HashMap;

public class FetchLoanAgreement extends BaseAPI<FetchLoanAgreementResponsePojo> {

    public FetchLoanAgreement() throws Exception {
        super(Uri.FETCH_LOAN_AGREEMENT_SHYLOCK, FetchLoanAgreementResponsePojo.class);
    }

    @Step
    public Response putWithoutQueryParams(String jsonRequestBody, HashMap<String, String> headerDetails) {
        return super.putWithoutQueryParams(headerDetails, jsonRequestBody);
    }
}
