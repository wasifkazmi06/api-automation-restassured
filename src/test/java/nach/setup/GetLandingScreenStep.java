package nach.setup;

import api.nach.setup.GetLandingPage;
import io.qameta.allure.Step;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.GetLandingPagePojo;
import java.util.HashMap;
import java.util.Map;

public class GetLandingScreenStep {

    static GetLandingPagePojo getLandingPagePojo;
    static GetLandingPage getLandingPage;

    static {
        try {
            getLandingPage = new GetLandingPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GetLandingScreenStep() throws Exception {
    }

    @Step
    public static void getLandingScreenValidUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("product", NACHData.bnplProduct);

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getLandingPagePojo = getLandingPage.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getLandingPagePojo.screenId, "401", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(getLandingPagePojo.screenType, "IRS_SUPPORTED_METHOD", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void getLandingScreenIncorrectAuth() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("product", NACHData.bnplProduct);

        headerDetails.put("authorization", "IncorrectAuthorization-1234567890-@#$");
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getLandingPagePojo = getLandingPage.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getLandingPagePojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(getLandingPagePojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");

    }

}
