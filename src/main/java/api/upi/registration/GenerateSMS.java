package api.upi.registration;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.registration.GenerateSMSPojo;

import java.util.HashMap;
import java.util.Map;

public class GenerateSMS extends BaseAPI<GenerateSMSPojo> {

    public GenerateSMS() throws Exception {
        super(Uri.GENERATE_SMS, GenerateSMSPojo.class);
    }
    @Step
    public GenerateSMSPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
