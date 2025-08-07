package api.deviceBinding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.deviceBinding.NotifySMSPojo;
import java.util.HashMap;
import java.util.Map;

public class NotifySMS extends BaseAPI<NotifySMSPojo> {

    public NotifySMS() throws Exception {
        super(Uri.NOTIFY_SMS_DEVICE_BINDING, NotifySMSPojo.class);
    }

    @Step
    public NotifySMSPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}