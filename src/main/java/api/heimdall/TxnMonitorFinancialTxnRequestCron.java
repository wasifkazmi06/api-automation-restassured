package api.heimdall;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.TxnMonitorCronPojo;

import java.util.HashMap;
import java.util.Map;

public class TxnMonitorFinancialTxnRequestCron extends BaseAPI<TxnMonitorCronPojo> {


    public TxnMonitorFinancialTxnRequestCron() throws Exception {
        super(Uri.TM_CRON_PROCESS_FINANCIAL_TXN_REQUEST, TxnMonitorCronPojo.class);
    }

    @Step
    public String postWithNoResponseBody(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.postWithNoResponseBody(queryParamDetails, headerDetails);
    }
}
