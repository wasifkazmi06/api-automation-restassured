package nach.setup;

import api.nach.setup.GetIRSStatus;
import api.nach.setup.GetLandingPage;
import io.qameta.allure.Step;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.GetIRSStatusPojo;
import pojos.nach.GetLandingPagePojo;

import java.util.HashMap;
import java.util.Map;

public class GetIRSStatusStep {

    static GetIRSStatus getIRSStatus;

    static {
        try {
            getIRSStatus = new GetIRSStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static GetIRSStatusPojo getIRSStatusPojo;


    public GetIRSStatusStep() throws Exception {
    }

    @Step
    public static void getIRSStatusStepValidUserDigio()  {

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

        getIRSStatusPojo = getIRSStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getIRSStatusPojo.repayStatus, "ACCEPTED", "");
        Assert.assertEquals(getIRSStatusPojo.nextRepayStep, "COMPLETED", "");
        Assert.assertEquals(getIRSStatusPojo.methodVendor, "NET_BANKING_DIGIO", "");

    }

    @Step
    public static void getIRSStatusStepValidUserRazorpay()  {

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

        getIRSStatusPojo = getIRSStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getIRSStatusPojo.repayStatus, "ACCEPTED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(getIRSStatusPojo.nextRepayStep, "COMPLETED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(getIRSStatusPojo.methodVendor, "NET_BANKING_RAZORPAY", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void getIRSStatusStepIncorrectAuth() {

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

        getIRSStatusPojo = getIRSStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getIRSStatusPojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(getIRSStatusPojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");;

    }
}
