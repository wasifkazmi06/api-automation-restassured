package api.cof.initiate;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.cof.initiate.InitiateResponsePojo;

import java.util.HashMap;
import java.util.Map;

public class COFInitiate extends BaseAPI<InitiateResponsePojo> {
    public COFInitiate() throws Exception
    {
        super(Uri.COF_INITIATE,InitiateResponsePojo.class);
    }

    @Step
    public InitiateResponsePojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
