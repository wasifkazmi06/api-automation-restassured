package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.GetAppOTPPojo;
import java.util.HashMap;
import java.util.Map;

public class GetAppOTP extends BaseAPI<GetAppOTPPojo> {
    public GetAppOTP() throws Exception {
            super(Uri.GET_APP_OTP, GetAppOTPPojo.class);
    }
    @Step
    public GetAppOTPPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
