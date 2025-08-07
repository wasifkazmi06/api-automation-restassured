package api.lazypay.juspay.repayment;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.LoginPojo;


import java.util.HashMap;
import java.util.Map;

public class LoginRegister extends BaseAPI<LoginPojo> {
    public LoginRegister() throws Exception {
        super(Uri.LOGIN, LoginPojo.class);
    }

    @Step
    public LoginPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }

}
