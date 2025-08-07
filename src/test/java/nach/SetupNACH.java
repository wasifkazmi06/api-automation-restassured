package nach;

import api.nach.setup.AuthenticateSetupNB;
import api.nach.setup.GetIRSStatus;
import api.nach.setup.InitiateIRS;
import api.platform.GetUserData;
import api.platform.UpdateUserStatus;
import io.restassured.response.Response;
import lazypay.LazypayConstants;
import lazypay.MakeTransaction;
import mbe.authentication.APPLoginUser;
import mbe.authentication.DeviceConfigTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.nach.AuthenticateSetupNBPojo;
import pojos.nach.GetIRSStatusPojo;
import pojos.nach.InitiateIRSPojo;
import pojos.platform.UpdateUserStatusPojo;
import pojos.platform.getUserData.UserData;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SetupNACH {

    static public InitiateIRS initiateIRS;
    static public InitiateIRSPojo initiateIRSPojo;
    static AuthenticateSetupNB authenticateSetupNB;
    static AuthenticateSetupNBPojo authenticateSetupNBPojo;
    static GetIRSStatus getIRSStatus;
    static public GetIRSStatusPojo getIRSStatusPojo;
    static public GetUserData getUserData;
    static public UserData userData;
    static public UpdateUserStatus updateUserStatus;
    static public Response updateUserStatusResponse;

    static String authorisation;
    static String NACHProduct = System.getProperty("NACHProduct");
    static String NACHType = System.getProperty("NACHType");
    static String NACHVendor = System.getProperty("NACHVendor");
    static String system = "COLLECTIONS";
    static String statusAccepted = "ACCEPTED";
    static String bankName;
    static String ifsc;
    static String updateUserRequest;
    static String  authenticateSetupNBRequest;
    static String razorPayTokenId;
    static String razorPayPaymentId;

    static {
        try {
            initiateIRS = new InitiateIRS();
            authenticateSetupNB = new AuthenticateSetupNB();
            getIRSStatus = new GetIRSStatus();
            getUserData = new GetUserData();
            updateUserStatus = new UpdateUserStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getIRSStatus()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        queryParamDetails.put("product", NACHData.bnplProduct);

        headerDetails.put("authorization", authorisation);
        headerDetails.put("appversion", DeviceConfigTest.currentAppVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        getIRSStatusPojo = getIRSStatus.get(queryParamDetails, headerDetails);

        Assert.assertEquals(getIRSStatusPojo.repayStatus, "ACCEPTED", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(getIRSStatusPojo.nextRepayStep, "COMPLETED", "Check if the service is up and the authentication is valid");
        Assert.assertTrue(getIRSStatusPojo.methodVendor.equals("NET_BANKING_RAZORPAY") || getIRSStatusPojo.methodVendor.equals("NET_BANKING_DIGIO"), "Check if the service is up and the authentication is valid");

    }

    public static void updateUserStatusPlatform()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        Map<String, Object> queryParamDetailsGetUserPlatform = new HashMap<>();
        HashMap<String, String> headerDetailsGetUserPlatform = new HashMap<>();

        queryParamDetailsGetUserPlatform.put("mobile",MakeTransaction.mobile);

        userData = getUserData.get(queryParamDetailsGetUserPlatform, headerDetailsGetUserPlatform);

        Assert.assertEquals(userData.getPrimaryMobile().getValue(), MakeTransaction.mobile, "User mobile not matching in platform get user response!");

        updateUserRequest = new StringTemplate(LazypayConstants.UPDATE_USER_STATUS_PLATFORM)
                .replace("userId", userData.getId())
                .replace("system", system)
                .replace("status", statusAccepted)
                .toString();

        updateUserStatusResponse = updateUserStatus.postReturnResponse(queryParamDetails, headerDetails, updateUserRequest);

        Assert.assertEquals(updateUserStatusResponse.getStatusCode(), 200, "Response Code not equal to 200. API call failed!");
    }

    public static void initiateNACH() throws Exception {

            HashMap<String, String> headerDetails = new HashMap<>();

            authorisation = APPLoginUser.LoginIntoApp(MakeTransaction.mobile);

            headerDetails.put("authorization", authorisation);
            headerDetails.put("appversion", DeviceConfigTest.currentAppVersion);
            headerDetails.put("userAgent", NACHData.userAgent);
            headerDetails.put("requestId",NACHData.requestId);
            headerDetails.put("deviceId",NACHData.deviceId);
            headerDetails.put("location",NACHData.location);
            headerDetails.put("deviceIP",NACHData.deviceIP);

            if(NACHVendor.equals("Digio"))
            {
                bankName = NACHData.bankNameDigio;
                ifsc = NACHData.ifscDigio;
            }
            else if(NACHVendor.equals("Razorpay"))
            {
                bankName = NACHData.bankNameRazorpay;
                ifsc = NACHData.ifscRazorpay;
            }
            String initiateIRSRequest = new StringTemplate(NACHConstants.INITIATE_IRS_REQUEST)
                    .replace("accountNo", String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999)))
                    .replace("bankName", bankName)
                    .replace("accountHolderName", NACHData.accountHolderNameDigio)
                    .replace("ifsc", ifsc)
                    .replace("product", NACHProduct)
                    .replace("repayMethod", NACHType)
                    .toString();

            initiateIRSPojo = initiateIRS.post(headerDetails, initiateIRSRequest);

            Assert.assertEquals(initiateIRSPojo.nextRepayStep, "SIGN", "Check if the service is up and the authentication is valid");
            Assert.assertEquals(initiateIRSPojo.status, "INITIATED", "Check if the service is up and the authentication is valid");
            Assert.assertEquals(initiateIRSPojo.repayMethod, "NET_BANKING", "Check if the service is up and the authentication is valid");
            Assert.assertTrue(initiateIRSPojo.vendor.equals("DIGIO") || initiateIRSPojo.vendor.equals("RAZOR_PAY"), "Check if the service is up and the authentication is valid");
            Assert.assertNotNull(initiateIRSPojo.repaySetupId, "Check if the service is up and the authentication is valid");
        }

        @Test
        public static void authenticateSetup() throws Exception {

        HashMap<String, String> headerDetails = new HashMap<>();

        /**
         * Step 1 - Initiate NACH
         */
        initiateNACH();

        headerDetails.put("authorization", authorisation);
        headerDetails.put("appversion", DeviceConfigTest.currentAppVersion);
        headerDetails.put("userAgent", NACHData.userAgent);
        headerDetails.put("requestId",NACHData.requestId);
        headerDetails.put("deviceId",NACHData.deviceId);
        headerDetails.put("location",NACHData.location);
        headerDetails.put("deviceIP",NACHData.deviceIP);

        /**
         * Step 2 - Authenticate NACH and update database to simulate successful setup
         */
        if(NACHVendor.equals("Digio"))
        {
            authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB_DIGIO)
                    .replace("product", NACHProduct)
                    .replace("repayMethod", SetupNACH.initiateIRSPojo.repayMethod)
                    .replace("repaySetupId", String.valueOf(SetupNACH.initiateIRSPojo.repaySetupId))
                    .replace("paymentId", SetupNACH.initiateIRSPojo.responseBody.mandateId)
                    .replace("id", SetupNACH.initiateIRSPojo.responseBody.mandateId)
                    .replace("npci_txn_id", ReturnRandomTxnId.returnTxnIDMethod("NPCI"))
                    .replace("vendor", SetupNACH.initiateIRSPojo.vendor)
                    .toString();

            authenticateSetupNBPojo = authenticateSetupNB.post(headerDetails, authenticateSetupNBRequest);
            NACHData.acceptNACHSetupDigio(String.valueOf(SetupNACH.initiateIRSPojo.repaySetupId));

        }

        else if(NACHVendor.equals("Razorpay"))
        {
            razorPayPaymentId = "pay_Ky1PV"+ReturnRandomTxnId.returnTxnIDMethod("RP_PID");
            razorPayTokenId = "token_Ky1PV"+ ReturnRandomTxnId.returnTxnIDMethod("RP_PID");

            authenticateSetupNBRequest = new StringTemplate(NACHConstants.AUTHENTICATE_SETUP_NB_RP)
                    .replace("product", NACHProduct)
                    .replace("repayMethod", SetupNACH.initiateIRSPojo.repayMethod)
                    .replace("repaySetupId", String.valueOf(SetupNACH.initiateIRSPojo.repaySetupId))
                    .replace("paymentId", razorPayPaymentId)
                    .replace("vendor", SetupNACH.initiateIRSPojo.vendor)
                    .toString();

            authenticateSetupNBPojo = authenticateSetupNB.post(headerDetails, authenticateSetupNBRequest);
            NACHData.acceptNACHSetupRazorpay(String.valueOf(initiateIRSPojo.repaySetupId), razorPayPaymentId, razorPayTokenId);
        }

        Assert.assertEquals(authenticateSetupNBPojo.screenId, "406", "Check if the service is up and the authentication is valid");
        Assert.assertEquals(authenticateSetupNBPojo.screenType, "IRS_TRANSACTION_COMPLETION_SCREEN", "Check if the service is up and the authentication is valid");

        /**
         * Step 3 - Update collection_setup to true in platform
         */
        updateUserStatusPlatform();

        /**
         * Step 4 - Get NACH setup status to confirm successful setup
         */
        getIRSStatus();
    }
}