package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.GetLoanPlansResponsePojo;

import java.util.HashMap;

public class GetLoanPlans extends BaseAPI<GetLoanPlansResponsePojo> {

    public GetLoanPlans() throws Exception {
        super(Uri.GET_LOAN_PLANS_SHYLOCK, GetLoanPlansResponsePojo.class);
    }

    @Step
    public Response getWithResponse(HashMap<String, String> headerDetails,String pathParam) {
        return super.getWithResponse(headerDetails,pathParam);
    }
}
