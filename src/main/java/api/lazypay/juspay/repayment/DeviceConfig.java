package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.DevicConfigPojo;



import java.util.HashMap;
import java.util.Map;

public class DeviceConfig extends BaseAPI<DevicConfigPojo> {

    public DeviceConfig() throws Exception {
        super(Uri.DEVICECONFIG, DevicConfigPojo.class);
    }

    @Step
    public DevicConfigPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
