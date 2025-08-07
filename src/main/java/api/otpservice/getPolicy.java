package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.getPolicyResponsePojo;

import java.util.HashMap;

public class getPolicy extends BaseAPI<getPolicyResponsePojo> {
    public getPolicy() throws Exception {
        super(Uri.OTP_SERVICE_GET_POLICY, getPolicyResponsePojo.class);
    }

    @Step
    public getPolicyResponsePojo get(HashMap<String, Object> queryparamdetails, HashMap<String, String> headerDetails) {
        return super.get(queryparamdetails, headerDetails);
    }
}
