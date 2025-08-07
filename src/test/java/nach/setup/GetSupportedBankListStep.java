package nach.setup;

import api.nach.setup.GetSupportedBankList;
import io.qameta.allure.Step;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.GetSupportedBankListPojo;

import java.util.HashMap;
import java.util.Map;

public class GetSupportedBankListStep {

    static GetSupportedBankList getSupportedBankList;

    static {
        try {
            getSupportedBankList = new GetSupportedBankList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static GetSupportedBankListPojo getSupportedBankListPojo;

    @Step
    public static void getSupportedBankListValidUser()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("product", NACHData.bnplProduct);
        queryParamDetails.put("paymentModeType", NACHData.paymentModeTypeNB);

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getSupportedBankListPojo = getSupportedBankList.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getSupportedBankListPojo.screenId, "NBBANKLIST01", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void getSupportedBankLisIncorrectAuth() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("product", NACHData.bnplProduct);
        queryParamDetails.put("paymentModeType", NACHData.paymentModeTypeNB);

        headerDetails.put("authorization", "IncorrectAuthorization-1234567890-@#$");
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getSupportedBankListPojo = getSupportedBankList.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getSupportedBankListPojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(getSupportedBankListPojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");

    }
}
