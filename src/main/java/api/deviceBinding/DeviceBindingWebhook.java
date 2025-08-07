package api.deviceBinding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.deviceBinding.DeviceBindingWebhookPojo;

import java.util.HashMap;
import java.util.Map;

public class DeviceBindingWebhook extends BaseAPI<DeviceBindingWebhookPojo> {

    public DeviceBindingWebhook() throws Exception {
        super(Uri.DEVICE_BINDING_WEBHOOK, DeviceBindingWebhookPojo.class);
    }

    @Step
    public Response postReturnResponse(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.postReturnResponse(queryParamDetails, headerDetails, "");
    }
}