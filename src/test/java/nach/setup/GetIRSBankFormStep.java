package nach.setup;

import api.nach.setup.GetIRSBankForm;
import io.qameta.allure.Step;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.GetIRSBankFormPojo;

import java.util.HashMap;
import java.util.Map;

public class GetIRSBankFormStep {

    static GetIRSBankForm getIRSBankForm;

    static {
        try {
            getIRSBankForm = new GetIRSBankForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static GetIRSBankFormPojo getLandingPagePojo;


    public GetIRSBankFormStep() throws Exception {
    }


    @Step
    public static void getIRSBankFormStepValidUser()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getLandingPagePojo = getIRSBankForm.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getLandingPagePojo.screenId, "402", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(getLandingPagePojo.screenType, "IRS_NB_FOM", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void getIRSBankFormStepIncorrectAuth()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", "IncorrectAuthorization-1234567890-@#$");
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getLandingPagePojo = getIRSBankForm.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getLandingPagePojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(getLandingPagePojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");

    }
}
