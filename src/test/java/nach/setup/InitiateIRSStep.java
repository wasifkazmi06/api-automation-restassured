package nach.setup;

import io.qameta.allure.Step;
import api.nach.setup.InitiateIRS;
import nach.NACHConstants;
import nach.NACHData;
import org.testng.Assert;
import pojos.nach.InitiateIRSPojo;
import util.StringTemplate;
import java.util.HashMap;

public class InitiateIRSStep {

    static public InitiateIRS initiateIRS;
    static InitiateIRSPojo initiateIRSPojo;
    static public String setupID;

    static {
        try {
            initiateIRS = new InitiateIRS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public InitiateIRSStep()  {
    }

    @Step
    public static void initiateIRSStepValidUserDigio() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoDigio)
                .replace("bankName", NACHData.bankNameDigio)
                .replace("accountHolderName", NACHData.accountHolderNameDigio)
                .replace("ifsc", NACHData.ifscDigio)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        setupID = String.valueOf(initiateIRSPojo.repaySetupId);

        Assert.assertEquals(initiateIRSPojo.nextRepayStep, "SIGN", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.status, "INITIATED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.repayMethod, "NET_BANKING", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.vendor, "DIGIO", "Check if the service is up and the authentication is valid");
        Assert.assertNotNull(initiateIRSPojo.repaySetupId, "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void initiateIRSStepValidUserRazorpay() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoRazorpay)
                .replace("bankName", NACHData.bankNameRazorpay)
                .replace("accountHolderName", NACHData.accountHolderNameRazorpay)
                .replace("ifsc", NACHData.ifscRazorpay)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        setupID = String.valueOf(initiateIRSPojo.repaySetupId);

        Assert.assertEquals(initiateIRSPojo.nextRepayStep, "SIGN", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.status, "INITIATED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.repayMethod, "NET_BANKING", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.vendor, "RAZOR_PAY", "Check if the service is up and the authentication is valid");
        Assert.assertNotNull(initiateIRSPojo.repaySetupId, "Check if the service is up and the authentication is valid");

    }
    @Step
    public static void initiateIRSStepValidMADUserDigio() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoDigioMAD)
                .replace("bankName", NACHData.bankNameDigioMAD)
                .replace("accountHolderName", NACHData.accountHolderNameDigioMAD)
                .replace("ifsc", NACHData.ifscDigioMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        setupID = String.valueOf(initiateIRSPojo.repaySetupId);

        Assert.assertEquals(initiateIRSPojo.nextRepayStep, "SIGN", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.status, "INITIATED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.repayMethod, "NET_BANKING", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.vendor, "DIGIO", "Check if the service is up and the authentication is valid");
        Assert.assertNotNull(initiateIRSPojo.repaySetupId, "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void initiateIRSStepValidMADUserRazorpay() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoRazorpayMAD)
                .replace("bankName", NACHData.bankNameRazorpayMAD)
                .replace("accountHolderName", NACHData.accountHolderNameRazorpayMAD)
                .replace("ifsc", NACHData.ifscRazorpayMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        setupID = String.valueOf(initiateIRSPojo.repaySetupId);

        Assert.assertEquals(initiateIRSPojo.nextRepayStep, "SIGN", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.status, "INITIATED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.repayMethod, "NET_BANKING", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(initiateIRSPojo.vendor, "RAZOR_PAY", "Check if the service is up and the authentication is valid");
        Assert.assertNotNull(initiateIRSPojo.repaySetupId, "Check if the service is up and the authentication is valid");

    }

    @Step
    public static void initiateIRSStepIncorrectAuth() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", "IncorrectAuthorization-1234567890-@#$");
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoRazorpayMAD)
                .replace("bankName", NACHData.bankNameRazorpayMAD)
                .replace("accountHolderName", NACHData.accountHolderNameRazorpayMAD)
                .replace("ifsc", NACHData.ifscRazorpayMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        Assert.assertEquals(initiateIRSPojo.title, "This is not a valid token", "Check that the authentication is invalid");
        Assert.assertEquals(initiateIRSPojo.description, "INVALID_TOKEN", "Check that the authentication is invalid");


    }

    @Step
    public static void initiateIRSStepNoAccountNumber() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", "")
                .replace("bankName", NACHData.bankNameRazorpayMAD)
                .replace("accountHolderName", NACHData.accountHolderNameRazorpayMAD)
                .replace("ifsc", NACHData.ifscRazorpayMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        Assert.assertEquals(initiateIRSPojo.title, "INVALID_REPAY_SETUP", "Check if the service is up and the authentication is valid and account number is not passed");

    }

    @Step
    public static void initiateIRSStepInvalidAccountNumber() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", "1234ABC%^&")
                .replace("bankName", NACHData.bankNameRazorpayMAD)
                .replace("accountHolderName", NACHData.accountHolderNameRazorpayMAD)
                .replace("ifsc", NACHData.ifscRazorpayMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        Assert.assertEquals(initiateIRSPojo.title, "Invalid account number!", "Check if the service is up and the authentication is valid and account number is invalid");

    }

    @Step
    public static void initiateIRSStepNoBankame() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoGenric)
                .replace("bankName", "")
                .replace("accountHolderName", NACHData.accountHolderNameRazorpayMAD)
                .replace("ifsc", NACHData.ifscRazorpayMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        Assert.assertEquals(initiateIRSPojo.type, "SOME_FIELDS_ARE_MISSING", "Check if the service is up and the authentication is valid and bank name is not passed");

    }

    @Step
    public static void initiateIRSStepNoName() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoGenric)
                .replace("bankName", NACHData.bankNameGenric)
                .replace("accountHolderName","")
                .replace("ifsc", NACHData.ifscRazorpayMAD)
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        Assert.assertEquals(initiateIRSPojo.type, "SOME_FIELDS_ARE_MISSING", "Check if the service is up and the authentication is valid and beneficiary name is not passed");

    }

    @Step
    public static void initiateIRSStepNoIFSC() {

        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", NACHSetupTest.authorization);
        headerDetails.put("appversion", NACHSetupTest.appVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                .replace("accountNo", NACHData.accountNoGenric)
                .replace("bankName", NACHData.bankNameGenric)
                .replace("accountHolderName",NACHData.ifscGenric)
                .replace("ifsc", "")
                .replace("product", NACHData.bnplProduct)
                .replace("repayMethod", NACHData.paymentModeTypeNB)
                .toString();

        initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

        Assert.assertEquals(initiateIRSPojo.type, "IFSC_CODE_NOT_PRESENT", "Check if the service is up and the authentication is valid and IFSC code is not passed");

    }
}
