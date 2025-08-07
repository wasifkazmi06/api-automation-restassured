package api.lazypay.userOnboarding;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.lazypay.transactionFlow.ValidateOTPPojo;
import pojos.lazypay.userOnboardingFlow.UserOnboardingPojo;

import java.util.HashMap;
import java.util.Map;

public class UserOnboardingV1 extends BaseAPI<UserOnboardingPojo> {
    public UserOnboardingV1() throws Exception {
        super(Uri.USER_ONBOARDINGV1, UserOnboardingPojo.class);
    }
    @Step
    public UserOnboardingPojo post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request) {
        return super.post(queryParamDetails, headerDetails, request);
    }
}




