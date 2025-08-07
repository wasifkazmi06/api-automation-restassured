package api.neobank.supportapis;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.support.preference.CustomerPref;

import java.util.HashMap;
import java.util.Map;

public class CustPreference extends BaseAPI<CustomerPref> {

    public CustPreference() throws Exception {
        super(Uri.NEO_CUSTOMER_PREFERENCE, CustomerPref.class);
    }
    @Step
    public CustomerPref post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
