package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.EligibilityPojo;

import java.util.HashMap;
import java.util.Map;

public class EligibilityV3 extends BaseAPI<EligibilityPojo> {
    public EligibilityV3() throws Exception {
        super(Uri.ELIGIBILITYV3, EligibilityPojo.class);
    }
    @Step
    public EligibilityPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
