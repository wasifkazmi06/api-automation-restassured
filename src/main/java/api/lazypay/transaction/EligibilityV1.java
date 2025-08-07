package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.EligibilityPojo;

import java.util.HashMap;
import java.util.Map;

public class EligibilityV1 extends BaseAPI<EligibilityPojo> {
    public EligibilityV1() throws Exception {
        super(Uri.ELIGIBILITYV1, EligibilityPojo.class);
    }
    @Step
    public EligibilityPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
