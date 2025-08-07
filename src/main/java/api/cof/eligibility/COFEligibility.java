package api.cof.eligibility;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.cof.eligibility.EligibiltyResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class COFEligibility extends BaseAPI<EligibiltyResponsePojo> {
    public COFEligibility() throws Exception
    {
        super(Uri.COF_ELIGIBILTY,EligibiltyResponsePojo.class);
    }

    @Step
    public EligibiltyResponsePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
