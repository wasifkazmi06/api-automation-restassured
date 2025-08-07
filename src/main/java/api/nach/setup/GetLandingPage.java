package api.nach.setup;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.EligibilityPojo;
import pojos.nach.GetLandingPagePojo;
import java.util.HashMap;
import java.util.Map;

public class GetLandingPage extends BaseAPI<GetLandingPagePojo> {
    public GetLandingPage() throws Exception {
            super(Uri.GET_LANDING_PAGE, GetLandingPagePojo.class);
    }
    @Step
    public GetLandingPagePojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
