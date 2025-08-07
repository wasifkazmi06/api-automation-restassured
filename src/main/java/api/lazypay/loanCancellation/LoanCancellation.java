package api.lazypay.loanCancellation;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.loanCancellationFlow.LoanCancellationPojo;

import java.util.HashMap;
import java.util.Map;

public class LoanCancellation extends BaseAPI<LoanCancellationPojo> {
    public LoanCancellation() throws Exception {
            super(Uri.LOAN_CANCELLATION, LoanCancellationPojo.class);
    }
    @Step
    public LoanCancellationPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
