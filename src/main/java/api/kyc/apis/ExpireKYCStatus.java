package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.kyc.apis.KYCStatusPojo;

import java.util.HashMap;
import java.util.Map;

public class ExpireKYCStatus extends BaseAPI<KYCStatusPojo> {


    public ExpireKYCStatus() throws Exception {
        super(Uri.KYC_EXPIRY_Status, KYCStatusPojo.class);
    }


    @Step
    public Response getWithParam(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.getWithPathPram(queryParamDetails, headerDetails,pathParam);

    }
}
