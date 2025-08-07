package api.mpl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.mpl.EligibilityPOJO;
import java.util.HashMap;
import java.util.Map;

public class EligibilityV0 extends BaseAPI<EligibilityPOJO> {
    public EligibilityV0() throws Exception {
            super(Uri.MPL_ELIGIBILITY_V0, EligibilityPOJO.class);
    }
    @Step
    public EligibilityPOJO post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
