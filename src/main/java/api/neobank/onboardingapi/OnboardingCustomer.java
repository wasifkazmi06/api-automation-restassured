package api.neobank.onboardingapi;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.onboardingapi.OnboardCustomerPojo;
import java.util.HashMap;
import java.util.Map;

public class OnboardingCustomer extends BaseAPI<OnboardCustomerPojo> {
    public OnboardingCustomer() throws Exception {
        super(Uri.NEO_ONBOARD_CUSTOMER, OnboardCustomerPojo.class);
    }
    @Step
    public OnboardCustomerPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
