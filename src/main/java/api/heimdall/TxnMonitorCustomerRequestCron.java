package api.heimdall;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.TxnMonitorCronPojo;

import java.util.HashMap;
import java.util.Map;

public class TxnMonitorCustomerRequestCron extends BaseAPI<TxnMonitorCronPojo> {


    public TxnMonitorCustomerRequestCron() throws Exception {
        super(Uri.TM_CRON_PROCESS_CUSTOMER_REQUEST, TxnMonitorCronPojo.class);
    }

    @Step
    public String postWithNoResponseBody(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.postWithNoResponseBody(queryParamDetails, headerDetails);
    }
}
