package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.nach.GetIRSStatusPojo;
import pojos.nach.GetLandingPagePojo;

import java.util.HashMap;
import java.util.Map;

public class GetIRSStatus extends BaseAPI<GetIRSStatusPojo> {
    public GetIRSStatus() throws Exception {
            super(Uri.GET_IRS_STATUS, GetIRSStatusPojo.class);
    }
    @Step
    public GetIRSStatusPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
