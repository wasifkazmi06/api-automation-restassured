package api.lazypay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.repaymentFlow.ProcessRepayPojo;

import java.util.HashMap;
import java.util.Map;

public class ProcessRepay extends BaseAPI<ProcessRepayPojo> {
        public ProcessRepay() throws Exception {
            super(Uri.PROCESSREPAY, ProcessRepayPojo.class);
        }
        @Step
        public ProcessRepayPojo postWithPathParams(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails,String jsonRequestBody, String pathParam) {
            return super.postWithPathParams(queryParamDetails, headerDetails ,jsonRequestBody ,pathParam);
        }
    }


