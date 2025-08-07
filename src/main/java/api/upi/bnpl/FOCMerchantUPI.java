package api.upi.bnpl;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import java.util.HashMap;
import java.util.Map;
import pojos.upi.bnpl.FOCMerchantUPIPojo;

public class FOCMerchantUPI extends BaseAPI<FOCMerchantUPIPojo> {

    public FOCMerchantUPI() throws Exception {
        super(Uri.FOCMERCHANTUPI, FOCMerchantUPIPojo.class);
    }
    @Step
    public FOCMerchantUPIPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
