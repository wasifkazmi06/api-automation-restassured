package api.billpay.complaints;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.billpayment.complaints.RegisterComplaintsPojo;

import java.util.HashMap;
import java.util.Map;

public class RegisterComplaint extends BaseAPI<RegisterComplaintsPojo> {

    public RegisterComplaint() throws Exception {
        super(Uri.GET_registerComplaint, RegisterComplaintsPojo.class);

    }
    @Step
    public RegisterComplaintsPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
