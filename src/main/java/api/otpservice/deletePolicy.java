package api.otpservice;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.otpservice.deletePolicyResponsePojo;

import java.util.HashMap;

public class deletePolicy extends BaseAPI<deletePolicyResponsePojo> {
    public deletePolicy() throws Exception {
        super(Uri.OTP_SERVICE_DELETE_POLICY, deletePolicyResponsePojo.class);
    }

    @Step
    public deletePolicyResponsePojo delete(HashMap<String, Object> queryparamdetails, HashMap<String, String> headerDetails) {
        return super.delete(queryparamdetails, headerDetails);
    }
}
