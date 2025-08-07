package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.transactionFlow.SignaturePojo;
import java.util.HashMap;
import java.util.Map;

public class GenerateSignature extends BaseAPI<SignaturePojo>{
    public GenerateSignature() throws Exception {
        super(Uri.SIGNATURE, SignaturePojo.class);
    }
    @Step
    public Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.postReturnResponse(queryParamDetails, headerDetails, "");
    }
}
