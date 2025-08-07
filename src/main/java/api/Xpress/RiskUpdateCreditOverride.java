package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.RiskUpdateCreditOverrideResponsePojo;

import java.util.HashMap;

public class RiskUpdateCreditOverride extends BaseAPI<RiskUpdateCreditOverrideResponsePojo> {


    public RiskUpdateCreditOverride() throws Exception{
        super(Uri.RISK_UPDATE_BNPL_LIMIT, RiskUpdateCreditOverrideResponsePojo.class);
    }

    @Step
    public Response postWithResponse(HashMap<String, String> headerDetails, String jsonBody) {
        return super.postWithResponse(headerDetails, jsonBody);
    }
}
