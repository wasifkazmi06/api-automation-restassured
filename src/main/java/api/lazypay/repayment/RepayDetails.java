
package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.RepayDetailsPojo;

import java.util.HashMap;
import java.util.Map;

public class RepayDetails extends BaseAPI<RepayDetailsPojo> {
    public RepayDetails() throws Exception {
        super(Uri.REPAYDETAILS, RepayDetailsPojo.class);
    }
    @Step
    public RepayDetailsPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }



}
