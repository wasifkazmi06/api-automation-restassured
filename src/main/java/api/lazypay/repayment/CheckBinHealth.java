package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.lazypay.repaymentFlow.CheckBinHealthPojo;
import pojos.lazypay.repaymentFlow.IsSIAllowedPojo;

import java.util.HashMap;
import java.util.Map;

public class CheckBinHealth extends BaseAPI<CheckBinHealthPojo> {
    public CheckBinHealth() throws Exception {
            super(Uri.CHECKBINHEALTH, CheckBinHealthPojo.class);
    }
    @Step
    public CheckBinHealthPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String pathParam) {
        return super.get(queryParamDetails, headerDetails, pathParam);
    }
}
