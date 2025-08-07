package api.heimdall;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.heimdall.TxnMonitorCronPojo;

import java.util.HashMap;
import java.util.Map;

public class TxnMonitorCreateTxnRequestData extends BaseAPI<TxnMonitorCronPojo> {


    public TxnMonitorCreateTxnRequestData() throws Exception {
        super(Uri.TM_CREATE_FINANCIAL_TXN_REQUEST, TxnMonitorCronPojo.class);
    }

    @Step
    public String getWithNoResponseBody(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.getWithNoResponseBody(queryParamDetails, headerDetails);
    }
}
