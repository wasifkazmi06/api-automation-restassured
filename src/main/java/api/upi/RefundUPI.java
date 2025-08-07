package api.upi;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.upi.RefundUPIPojo;
import java.util.HashMap;
import java.util.Map;

public class RefundUPI extends BaseAPI<RefundUPIPojo> {
    public RefundUPI() throws Exception {
        super(Uri.REFUND_UPI, RefundUPIPojo.class);
    }
    @Step
    public RefundUPIPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
