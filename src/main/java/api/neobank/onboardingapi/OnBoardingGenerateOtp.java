package api.neobank.onboardingapi;

import api.BaseAPI;
import constants.Uri;
import io.qameta.allure.Step;
import pojos.neobank.onboardingapi.OnboardingOtp;
import java.util.HashMap;
import java.util.Map;

public class OnBoardingGenerateOtp extends BaseAPI<OnboardingOtp> {
    public OnBoardingGenerateOtp() throws Exception
    {
        super(Uri.NEO_INITIATE_ONBOARDING, OnboardingOtp.class);
    }
    @Step
    public OnboardingOtp post(Map<String, Object> queryParamDetails, HashMap<String, String> headerDetails, String request)
    {
        return super.post(queryParamDetails, headerDetails,request);
    }
}
