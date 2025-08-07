package api.lazypay.transaction;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.GetOTPByIDPojo;
import java.util.HashMap;
import java.util.Map;

public class GetOTPByID extends BaseAPI<GetOTPByIDPojo> {
    public GetOTPByID() throws Exception {
            super(Uri.GET_OTP_BY_ID, GetOTPByIDPojo.class);
    }
    @Step
    public GetOTPByIDPojo get(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails) {
        return super.get(queryParamDetails, headerDetails);
    }
}
