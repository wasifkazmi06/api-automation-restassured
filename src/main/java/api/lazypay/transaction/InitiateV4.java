package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.EligibilityPojo;
import pojos.lazypay.transactionFlow.InitiatePojo;

import java.util.HashMap;
import java.util.Map;

public class InitiateV4 extends BaseAPI<InitiatePojo> {
    public InitiateV4() throws Exception {
        super(Uri.INITIATE_PAYV4, InitiatePojo.class);
    }
    @Step
    public InitiatePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
