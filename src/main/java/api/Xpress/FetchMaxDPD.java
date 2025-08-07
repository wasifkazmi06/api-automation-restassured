package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.DisbursalAPIPojo;
import pojos.Xpress.LoanDetailsPojo;

import java.util.HashMap;

public class FetchMaxDPD extends BaseAPI<LoanDetailsPojo> {
    public FetchMaxDPD() throws Exception {
        super(Uri.CHECK_MAX_DPD, LoanDetailsPojo.class);
    }

    @Step
    public Response get(HashMap<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);
    }
}
