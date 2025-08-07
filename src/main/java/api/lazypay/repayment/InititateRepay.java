package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.InitiateRepayPojo;
import pojos.lazypay.repaymentFlow.ProcessRepayPojo;

import java.util.HashMap;
import java.util.Map;

public class InititateRepay extends BaseAPI<InitiateRepayPojo>{
    public InititateRepay() throws Exception {
        super(Uri.INITIATEREPAY, InitiateRepayPojo.class);
    }
    @Step
    public InitiateRepayPojo postWithPathParams(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody, String pathParam)
    {
        return super.postWithPathParams(queryParamDetails, headerDetails ,jsonRequestBody ,pathParam);
    }
}
