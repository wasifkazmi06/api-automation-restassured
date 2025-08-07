package api.deviceBinding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.deviceBinding.GenerateSMSPojo;
import java.util.HashMap;
import java.util.Map;

public class GenerateSMS extends BaseAPI<GenerateSMSPojo> {
    public GenerateSMS() throws Exception {
        super(Uri.GENERATE_SMS_DEVICE_BINDING, GenerateSMSPojo.class);
    }

    @Step
    public GenerateSMSPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}