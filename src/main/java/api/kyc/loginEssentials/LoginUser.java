package api.kyc.loginEssentials;
import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.kyc.loginEssentials.LoginPojo;

import java.util.HashMap;
import java.util.Map;

public class LoginUser extends BaseAPI<LoginPojo>  {
    public LoginUser() throws Exception {
        super(Uri.LAZYPAY_LOGIN, LoginPojo.class);
    }

    @Step
    public LoginPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String jsonRequestBody) {
        return super.post(queryParamDetails, headerDetails,jsonRequestBody);
    }
}
