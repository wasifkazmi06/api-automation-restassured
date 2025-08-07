package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.CashLoanOfferingResponsePojo;

import java.util.HashMap;

public class CashLoanOffering extends BaseAPI<CashLoanOfferingResponsePojo> {

    public CashLoanOffering() throws Exception{
        super(Uri.CASH_LOAN_OFFERING_SHYLOCK, CashLoanOfferingResponsePojo.class);
    }

    @Step
    public Response putWithoutQueryParams(HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.putWithoutQueryParams(headerDetails, jsonRequestBody);
    }
}

