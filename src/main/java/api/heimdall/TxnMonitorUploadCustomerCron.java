package api.heimdall;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.TxnMonitorCronPojo;

import java.util.HashMap;
import java.util.Map;

public class TxnMonitorUploadCustomerCron extends BaseAPI<TxnMonitorCronPojo> {


    public TxnMonitorUploadCustomerCron() throws Exception {
        super(Uri.TM_CRON_UPLOAD_CUSTOMER, TxnMonitorCronPojo.class);
    }

    @Step
    public TxnMonitorCronPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
