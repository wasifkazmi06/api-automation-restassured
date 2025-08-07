package nach.setup;

import api.nach.setup.UpdateRepaymentAmountType;
import io.qameta.allure.Step;
import nach.NACHConstants;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.UpdateRepaymentAmountTypePojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class UpdateRepaymentAmountTypeStep {

    static UpdateRepaymentAmountTypePojo updateRepaymentAmountTypePojo;
    static UpdateRepaymentAmountType updateRepaymentAmountType;

    static {
        try {
            updateRepaymentAmountType = new UpdateRepaymentAmountType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UpdateRepaymentAmountTypeStep() {
    }

    @Step
    public static void updateRepaymentAmountTypeValidUser()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("flowType", NACHData.flowType);

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String updateRepaymentAmountTypeRequest = new StringTemplate(NACHConstants.UPDATE_REPAYMENT_AMOUNT_TYPE_REQUEST)
                .replace("repaymentAmountType", NACHData.repaymentAmountTypeFull).toString();

        updateRepaymentAmountTypePojo = updateRepaymentAmountType.post(queryParamDetails, headerDetails, updateRepaymentAmountTypeRequest);

        Assert.assertEquals(updateRepaymentAmountTypePojo.screenId, "401", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(updateRepaymentAmountTypePojo.screenType, "IRS_SUPPORTED_METHOD", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void updateRepaymentAmountTypeValidMADUser() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("flowType", NACHData.flowType);

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String updateRepaymentAmountTypeRequest = new StringTemplate(NACHConstants.UPDATE_REPAYMENT_AMOUNT_TYPE_REQUEST)
                .replace("repaymentAmountType", NACHData.repaymentAmountTypeMAD).toString();

        updateRepaymentAmountTypePojo = updateRepaymentAmountType.post(queryParamDetails, headerDetails, updateRepaymentAmountTypeRequest);

        Assert.assertEquals(updateRepaymentAmountTypePojo.screenId, "401", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(updateRepaymentAmountTypePojo.screenType, "IRS_SUPPORTED_METHOD", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void updateRepaymentAmountTypeIncorrectAuth() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("flowType", NACHData.flowType);

        headerDetails.put("authorization", "IncorrectAuthorization-1234567890-@#$");
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String updateRepaymentAmountTypeRequest = new StringTemplate(NACHConstants.UPDATE_REPAYMENT_AMOUNT_TYPE_REQUEST)
                .replace("repaymentAmountType", NACHData.repaymentAmountTypeMAD).toString();

        updateRepaymentAmountTypePojo = updateRepaymentAmountType.post(queryParamDetails, headerDetails, updateRepaymentAmountTypeRequest);

        Assert.assertEquals(updateRepaymentAmountTypePojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(updateRepaymentAmountTypePojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");

    }
}
