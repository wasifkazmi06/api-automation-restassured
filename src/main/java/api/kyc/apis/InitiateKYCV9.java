package api.kyc.apis;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.apis.InitiateKYCV9Pojo;
import api.BaseAPI;

import java.util.HashMap;
import java.util.Map;


public class InitiateKYCV9 extends BaseAPI<InitiateKYCV9Pojo>{
    public InitiateKYCV9() throws Exception {
        super(Uri.INITIATE_KYCV9, InitiateKYCV9Pojo.class);
    }

    @Step
    public InitiateKYCV9Pojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
