package lazypay.juspay.repaymentFlow;

import api.lazypay.juspay.repayment.GetTransactionSuccess;
import api.lazypay.juspay.repayment.InitiateTransactionMBE;
import com.fasterxml.jackson.databind.JsonNode;

import io.qameta.allure.Step;
import lazypay.LazypayConstants;

import mbe.authentication.AuthTestData;
import org.json.JSONObject;
import pojos.lazypay.juspay.repaymentFlow.GetTransactionSuccessPojo;
import pojos.lazypay.juspay.repaymentFlow.InitiateTransactionMBEPojo;
import util.StringTemplate;
import org.testng.Assert;
import java.util.HashMap;
import java.util.Map;

import static lazypay.juspay.repaymentFlow.GetPayBillViewSteps.totaloutStandingAmount;
import static mbe.authentication.APPLoginUser.OauthToken;


public class InitiateRepayProcessSteps {

    public static InitiateTransactionMBEPojo initiateTransactionMBEPojo = new InitiateTransactionMBEPojo();
    public static InitiateTransactionMBE initiateTransactionMBE;
    public static GetTransactionSuccess getTransactionSuccess;
    public static String transactionId;
    public static String orderId;


    static {
        try {
            initiateTransactionMBE = new InitiateTransactionMBE();
            getTransactionSuccess = new GetTransactionSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public InitiateRepayProcessSteps() throws Exception {
    }

    @Step

    public void verifyRepaymentUsingUPI() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("authorization", OauthToken);
        headerDetails.put("PF-App-Lock-Token", AuthTestData.appLockToken);
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", AuthTestData.deviceBindingId);
        headerDetails.put("PF-Location", AuthTestData.location);
        headerDetails.put("PF-Product-Type", AuthTestData.productBNPL);

        String initiateTransactionMBERequest = new StringTemplate(LazypayConstants.INITIATE_REQUEST)
                .replace("amountToPay", totaloutStandingAmount) //need to fetch from DB
                .replace("source", RepaymentData_JP.SOURCE)
                .replace("productType",RepaymentData_JP.PRODUCT)
                .replace("paymentMode",RepaymentData_JP.UPI_PAYMENT_METHOD)
                .replace("bankName","")
                .replace("shortName","")
                .replace("bankCode","")
                .replace("cardType","")
                .replace("payWithSavedCard","false")
                .replace("vpa",RepaymentData_JP.USER_TEST_VPA_JP)
                .toString();

        initiateTransactionMBEPojo = initiateTransactionMBE.post(headerDetails, initiateTransactionMBERequest);
        JsonNode orderIdFrompayload = initiateTransactionMBEPojo.getPayload().get("orderId");
        transactionId = initiateTransactionMBEPojo.txnId;
        orderId = orderIdFrompayload.asText();

        Assert.assertNotNull(orderId, "OrderID  is not correct or Invalid");
        Assert.assertNotNull(transactionId, "Txn ID is not correct or Invalid");

        GetTransactionStatusSteps.getTransactionStatusMBE();
        Assert.assertTrue(GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("REPAYMENT_SUCCESS_ANIMATION") ||
                        GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("TRANSACTION_POLL_STATUS"),
                "Transaction is not in progress or failed");
    }


    @Step

    public void verifyRepaymentUsingDC() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("authorization", OauthToken);
        headerDetails.put("PF-App-Lock-Token", AuthTestData.appLockToken);
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", AuthTestData.deviceBindingId);
        headerDetails.put("PF-Location", AuthTestData.location);
        headerDetails.put("PF-Product-Type", AuthTestData.productBNPL);

        String initiateTransactionMBERequest = new StringTemplate(LazypayConstants.INITIATE_REQUEST)
                .replace("amountToPay", totaloutStandingAmount) //need to fetch from DB
                .replace("source", RepaymentData_JP.SOURCE)
                .replace("productType",RepaymentData_JP.PRODUCT)
                .replace("paymentMode",RepaymentData_JP.DC_PAYMENT_METHOD)
                .replace("bankName",RepaymentData_JP.BANK_NAME)
                .replace("shortName",RepaymentData_JP.SHORT_NAME)
                .replace("bankCode",RepaymentData_JP.BANK_CODE)
                .replace("cardType","DEBIT")
                .replace("payWithSavedCard","false")
                .replace("vpa","")
                .toString();

        JSONObject initiateTransactionMBERequestJson = new JSONObject(initiateTransactionMBERequest);
        initiateTransactionMBERequestJson.remove("shortName");
        initiateTransactionMBERequestJson.remove("bankCode");
        initiateTransactionMBERequestJson.remove("vpa");


        initiateTransactionMBEPojo = initiateTransactionMBE.post(headerDetails, initiateTransactionMBERequest);
        JsonNode orderIdFrompayload = initiateTransactionMBEPojo.getPayload().get("orderId");
        transactionId = initiateTransactionMBEPojo.txnId;
        orderId = orderIdFrompayload.asText();

        Assert.assertNotNull(orderId, "OrderID  is not correct or Invalid");
        Assert.assertNotNull(transactionId, "Txn ID is not correct or Invalid");

        GetTransactionStatusSteps.getTransactionStatusMBE();
        Assert.assertTrue(GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("REPAYMENT_SUCCESS_ANIMATION") ||
                        GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("TRANSACTION_POLL_STATUS"),
                "Transaction is not in progress or failed");
    }


    @Step
    public void verifyRepaymentUsingCC() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("authorization", OauthToken);
        headerDetails.put("PF-App-Lock-Token", AuthTestData.appLockToken);
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", AuthTestData.deviceBindingId);
        headerDetails.put("PF-Location", AuthTestData.location);
        headerDetails.put("PF-Product-Type", AuthTestData.productBNPL);

        String initiateTransactionMBERequest = new StringTemplate(LazypayConstants.INITIATE_REQUEST)
                .replace("amountToPay", totaloutStandingAmount) //need to fetch from DB
                .replace("source", RepaymentData_JP.SOURCE)
                .replace("productType",RepaymentData_JP.PRODUCT)
                .replace("paymentMode",RepaymentData_JP.CC_PAYMENT_METHOD)
                .replace("bankName",RepaymentData_JP.BANK_NAME)
                .replace("shortName","")
                .replace("bankCode","")
                .replace("cardType","CREDIT")
                .replace("payWithSavedCard","false")
                .replace("vpa","")
                .toString();


        initiateTransactionMBEPojo = initiateTransactionMBE.post(headerDetails, initiateTransactionMBERequest);

        JsonNode orderIdFrompayload = initiateTransactionMBEPojo.getPayload().get("orderId");
        transactionId = initiateTransactionMBEPojo.txnId;
        orderId = orderIdFrompayload.asText();

        Assert.assertNotNull(orderId, "OrderID  is not correct or Invalid");
        Assert.assertNotNull(transactionId, "Txn ID is not correct or Invalid");

        GetTransactionStatusSteps.getTransactionStatusMBE();
        Assert.assertTrue(GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("REPAYMENT_SUCCESS_ANIMATION") ||
                        GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("TRANSACTION_POLL_STATUS"),
                "Transaction is not in progress or failed");
    }


    @Step
    public void verifyRepaymentUsingNB() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("authorization", OauthToken);
        headerDetails.put("PF-App-Lock-Token", AuthTestData.appLockToken);
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", AuthTestData.deviceBindingId);
        headerDetails.put("PF-Location", AuthTestData.location);
        headerDetails.put("PF-Product-Type", AuthTestData.productBNPL);

        String initiateTransactionMBERequest = new StringTemplate(LazypayConstants.INITIATE_REQUEST)
                .replace("amountToPay", totaloutStandingAmount)
                .replace("source", RepaymentData_JP.SOURCE)
                .replace("productType",RepaymentData_JP.PRODUCT)
                .replace("paymentMode",RepaymentData_JP.NB_PAYMENT_METHOD)
                .replace("bankName",RepaymentData_JP.BANK_NAME)
                .replace("shortName",RepaymentData_JP.SHORT_NAME)
                .replace("bankCode",RepaymentData_JP.BANK_CODE)
                .replace("cardType","")
                .replace("payWithSavedCard","")
                .replace("vpa","")
                .toString();


        initiateTransactionMBEPojo = initiateTransactionMBE.post(headerDetails, initiateTransactionMBERequest);

        JsonNode orderIdFrompayload = initiateTransactionMBEPojo.getPayload().get("orderId");
        transactionId = initiateTransactionMBEPojo.txnId;
        orderId = orderIdFrompayload.asText();

        Assert.assertNotNull(orderId, "OrderID  is not correct or Invalid");
        Assert.assertNotNull(transactionId, "Txn ID is not correct or Invalid");

        GetTransactionStatusSteps.getTransactionStatusMBE();
        Assert.assertTrue(GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("REPAYMENT_SUCCESS_ANIMATION") ||
                        GetTransactionStatusSteps.repaymentActionText.equalsIgnoreCase("TRANSACTION_POLL_STATUS"),
                "Transaction is not in progress or failed");
    }

    @Step
    public void verifyRepaymentUsingUPIWithIncorrectAmount() throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("authorization", OauthToken);
        headerDetails.put("PF-App-Lock-Token", AuthTestData.appLockToken);
        headerDetails.put("PF-App-Version", JPRepaymentTests.appVersion);
        headerDetails.put("User-Agent", AuthTestData.userAgent);
        headerDetails.put("PF-OS-Version", AuthTestData.osversion);
        headerDetails.put("PF-Device-Manufaturer", AuthTestData.deviceManufacturer);
        headerDetails.put("PF-Sim-Ids", AuthTestData.simIds);
        headerDetails.put("PF-Device-Model", AuthTestData.deviceModel);
        headerDetails.put("PF-Device-Type", AuthTestData.deviceType);
        headerDetails.put("PF-Device-Id", AuthTestData.deviceId);
        headerDetails.put("PF-Client-Version-Code", AuthTestData.clientVersionCode);
        headerDetails.put("PF-Device-Binding-Id", AuthTestData.deviceBindingId);
        headerDetails.put("PF-Location", AuthTestData.location);
        headerDetails.put("PF-Product-Type", AuthTestData.productBNPL);

        String initiateTransactionMBERequest = new StringTemplate(LazypayConstants.INITIATE_REQUEST)
                .replace("amountToPay", String.valueOf(Double.valueOf(totaloutStandingAmount)- 100)) //need to fetch from DB
                .replace("source", RepaymentData_JP.SOURCE)
                .replace("productType",RepaymentData_JP.PRODUCT)
                .replace("paymentMode",RepaymentData_JP.NB_PAYMENT_METHOD)
                .replace("bankName",RepaymentData_JP.BANK_NAME)
                .replace("shortName",RepaymentData_JP.SHORT_NAME)
                .replace("bankCode",RepaymentData_JP.BANK_CODE)
                .replace("cardType","")
                .replace("payWithSavedCard","")
                .replace("vpa","")
                .toString();


        initiateTransactionMBEPojo = initiateTransactionMBE.post(headerDetails, initiateTransactionMBERequest);

        Assert.assertTrue(initiateTransactionMBEPojo.getPayload().get("subtitle").asText().contains("Amount should be greater than or equals 1."),"invalid amount should be sent in request");
        Assert.assertEquals(initiateTransactionMBEPojo.getPayload().get("errorCode").asText(),"INVALID_AMOUNT", "invalid amount should be sent in request");

    }
}





