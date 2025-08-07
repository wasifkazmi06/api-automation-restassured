package nach.setup;

import api.nach.setup.AuthenticateSetupNB;
import io.qameta.allure.Step;
import nach.NACHConstants;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.AuthenticateSetupNBPojo;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.util.HashMap;

public class AuthenticateSetupNBStep {

    static AuthenticateSetupNB authenticateSetupNB;
    static public String razorPayPaymentId;
    static {
        try {
            authenticateSetupNB = new AuthenticateSetupNB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static AuthenticateSetupNBPojo authenticateSetupNBPojo;


    public AuthenticateSetupNBStep() {
    }

    @Step
    public static void authenticateSetupNBValidUserDigio() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        /*String respMessage = "{\"txn_id\":\"0.3233367117344529\",\"id\":\""+ InitiateIRSStep.initiateIRSPojo.responseBody.mandateId
                +"\",\"message\":\"Success\",\"npci_txn_id\":\""+ ReturnRandomTxnId.returnTxnIDMethod("NPCI") +"\"}";

        String authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", InitiateIRSStep.initiateIRSPojo.repayMethod)
                .replace("repaySetupId", String.valueOf(InitiateIRSStep.initiateIRSPojo.repaySetupId))
                .replace("paymentId", InitiateIRSStep.initiateIRSPojo.responseBody.mandateId)
                .replace("respMessage", respMessage)
                .replace("vendor", InitiateIRSStep.initiateIRSPojo.vendor)
                .toString();*/

        /*JSONObject respMessage = new JSONObject();
        respMessage.put("id", InitiateIRSStep.initiateIRSPojo.responseBody.mandateId);
        respMessage.put("txn_id", "0.3233367117344529");
        respMessage.put("message", "Success");
        respMessage.put("npci_txn_id", ReturnRandomTxnId.returnTxnIDMethod("NPCI"));

        String authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", InitiateIRSStep.initiateIRSPojo.repayMethod)
                .replace("repaySetupId", String.valueOf(InitiateIRSStep.initiateIRSPojo.repaySetupId))
                .replace("paymentId", InitiateIRSStep.initiateIRSPojo.responseBody.mandateId)
                .replace("respMessage", respMessage.toString())
                .replace("vendor", InitiateIRSStep.initiateIRSPojo.vendor)
                .toString();*/

        String authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB_DIGIO)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", InitiateIRSStep.initiateIRSPojo.repayMethod)
                .replace("repaySetupId", String.valueOf(InitiateIRSStep.initiateIRSPojo.repaySetupId))
                .replace("paymentId", InitiateIRSStep.initiateIRSPojo.responseBody.mandateId)
                .replace("id", InitiateIRSStep.initiateIRSPojo.responseBody.mandateId)
                .replace("npci_txn_id", ReturnRandomTxnId.returnTxnIDMethod("NPCI"))
                .replace("vendor", InitiateIRSStep.initiateIRSPojo.vendor)
                .toString();

        authenticateSetupNBPojo = authenticateSetupNB.post(headerDetails, authenticateSetupNBRequest);

        Assert.assertEquals(authenticateSetupNBPojo.screenId, "406", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(authenticateSetupNBPojo.screenType, "IRS_TRANSACTION_COMPLETION_SCREEN", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void authenticateSetupNBValidUserRazorpay() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        razorPayPaymentId = "pay_Ky1PV"+ReturnRandomTxnId.returnTxnIDMethod("RP_PID");

        String authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB_RP)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", InitiateIRSStep.initiateIRSPojo.repayMethod)
                .replace("repaySetupId", String.valueOf(InitiateIRSStep.initiateIRSPojo.repaySetupId))
                .replace("paymentId", razorPayPaymentId)
                .replace("vendor", InitiateIRSStep.initiateIRSPojo.vendor)
                .toString();

        authenticateSetupNBPojo = authenticateSetupNB.post(headerDetails, authenticateSetupNBRequest);

        Assert.assertEquals(authenticateSetupNBPojo.screenId, "406", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(authenticateSetupNBPojo.screenType, "IRS_TRANSACTION_COMPLETION_SCREEN", "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void authenticateSetupIncorrectAuth() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", "IncorrectAuthorization-1234567890-@#$");
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        razorPayPaymentId = "pay_Ky1PV"+ReturnRandomTxnId.returnTxnIDMethod("RP_PID");

        String authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB_RP)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .replace("repaySetupId", "1234")
                .replace("paymentId", razorPayPaymentId)
                .replace("vendor", "DIGIO")
                .toString();

        authenticateSetupNBPojo = authenticateSetupNB.post(headerDetails, authenticateSetupNBRequest);

        Assert.assertEquals(authenticateSetupNBPojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(authenticateSetupNBPojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");

    }
}
