package api.Xpress;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Xpress.AgreementGetOtpResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class AgreementGetOtp extends BaseAPI<Response> {

    public AgreementGetOtp() throws Exception {
        super(Uri.AGREEMENT_GET_OTP_SHYLOCK, Response.class);
    }

    @Step
    public Response postWithResponse(String jsonRequestBody, HashMap<String, String> headerDetails) {
        Map<String, Object> queryParamDetails = new HashMap<>();
        return super.postWithResponse(queryParamDetails,headerDetails, jsonRequestBody);
    }

}
