package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.kyc.apis.KYCStatusPojo;

import java.util.HashMap;
import java.util.Map;

public class FetchKYCStatus extends BaseAPI<KYCStatusPojo> {


    public FetchKYCStatus() throws Exception {
        super(Uri.KYC_STATUS, KYCStatusPojo.class);
    }


    @Step
    public Response getWithResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithResponse(queryParamDetails, headerDetails);

    }
}
