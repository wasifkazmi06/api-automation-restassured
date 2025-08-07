package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.createPolicyResponsePojo;
import java.util.HashMap;

public class createPolicy extends BaseAPI<createPolicyResponsePojo> {
    public createPolicy() throws Exception {
        super(Uri.OTP_SERVICE_CREATE_POLICY, createPolicyResponsePojo.class);
    }
    @Step
    public createPolicyResponsePojo post(HashMap<String, Object> queryparamdetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryparamdetails,headerDetails, jsonRequestBody);
    }
}
