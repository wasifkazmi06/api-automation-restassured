package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.SelectedLoanPlanDetailsResponsePojo;

import java.util.HashMap;

public class SelectedLoanPlanDetails extends BaseAPI<SelectedLoanPlanDetailsResponsePojo> {

    public SelectedLoanPlanDetails() throws Exception {
        super(Uri.SELECTED_LOAN_PLANS_SHYLOCK, SelectedLoanPlanDetailsResponsePojo.class);
    }

    @Step
    public Response getWithResponse(String pathParam, HashMap<String, String> headerDetails) {
        return super.getWithResponse(headerDetails, pathParam);
    }
}
