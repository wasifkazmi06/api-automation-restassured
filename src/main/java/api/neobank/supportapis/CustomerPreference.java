package api.neobank.supportapis;

import io.qameta.allure.Step;
import api.BaseAPI;
import constants.Uri;
import pojos.neobank.support.preference.CustomerPref;

import java.util.HashMap;
import java.util.Map;

public class CustomerPreference extends BaseAPI<CustomerPref> {
    public CustomerPreference() throws Exception {
        super(Uri.NEO_CUSTOMER_PREFERENCE, CustomerPref.class);
    }
    @Step
    public CustomerPref post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
