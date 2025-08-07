package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.CheckBinHealthPojo;
import pojos.lazypay.repaymentFlow.PreferredMethodPojo;
import java.util.HashMap;
import java.util.Map;

public class PreferredMethod extends BaseAPI<PreferredMethodPojo> {
public PreferredMethod() throws Exception {
        super(Uri.PREFERREDMETHOD, PreferredMethodPojo.class);
        }
@Step
public PreferredMethodPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
        }
}
