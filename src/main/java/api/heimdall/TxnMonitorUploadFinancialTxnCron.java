package api.heimdall;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.TxnMonitorCronPojo;

import java.util.HashMap;
import java.util.Map;

public class TxnMonitorUploadFinancialTxnCron extends BaseAPI<TxnMonitorCronPojo> {


    public TxnMonitorUploadFinancialTxnCron() throws Exception {
        super(Uri.TM_CRON_UPLOAD_FINANCIAL_TXN, TxnMonitorCronPojo.class);
    }

    @Step
    public TxnMonitorCronPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails, jsonRequestBody);
    }
}
