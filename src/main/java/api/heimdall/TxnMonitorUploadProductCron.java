package api.heimdall;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.TxnMonitorCronPojo;

import java.util.HashMap;
import java.util.Map;

public class TxnMonitorUploadProductCron extends BaseAPI<TxnMonitorCronPojo> {


    public TxnMonitorUploadProductCron() throws Exception {
        super(Uri.TM_CRON_UPLOAD_PRODUCT, TxnMonitorCronPojo.class);
    }

    @Step
    public TxnMonitorCronPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
