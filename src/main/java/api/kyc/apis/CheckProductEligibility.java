package api.kyc.apis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.CheckEligibilityPojo;


import java.util.HashMap;
import java.util.Map;

public class CheckProductEligibility  extends BaseAPI<CheckEligibilityPojo> {


    public CheckProductEligibility() throws Exception {
        super(Uri.CHECK_ELIGIBILITY, CheckEligibilityPojo.class);
    }
    @Step
    public CheckEligibilityPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
