package lazypay.transactionFlow;

import api.lazypay.transaction.InitiateV5;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class InitiatePayV5Tests {

    public InitiatePayV5Tests() {
    }

    public static String signature;
    static public String MTX;
    static InitiateV5 initiateV5;

    static {
        try {
            initiateV5 = new InitiateV5();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay with source = CITRUS_SDK")
    @Feature("InitiatePayV5")
    @Test(priority = 1, groups = {"transaction", "regression", "sanity"})
    public static void initiateV5LPSourceCitrusSDK() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("To data types of initiate v5 response when source is CITRUS_SDK")
    @Feature("InitiatePayV5")
    @Test(priority = 2, groups = {"transaction", "regression", "sanity"})
    public void initiatev5DataTypeSourceCitrusSDK() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay with source = CITRUS_SSLV2")
    @Feature("InitiatePayV5")
    @Test(priority = 3, groups = {"transaction", "regression", "sanity"})
    public static void initiateV5LPSourceCitrusSSLV2() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID2);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("To data types of initiate v5 response when source is CITRUS_SSLV2")
    @Feature("InitiatePayV5")
    @Test(priority = 4, groups = {"transaction", "regression", "sanity"})
    public void initiatev5DataTypeSourceCitrusSSLV2() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay with source = STANDALONE_API")
    @Feature("InitiatePayV5")
    @Test(priority = 5, groups = {"transaction", "regression", "sanity"})
    public static void initiateV5LPSourceStandaloneAPI() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(1), "AUTO_DEBIT");
    }

    @Description("To data types of initiate v5 response when source is STANDALONE_API")
    @Feature("InitiatePayV5")
    @Test(priority = 6, groups = {"transaction", "regression", "sanity"})
    public void initiatev5DataTypeSourceStandaloneAPI() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.checkoutPageUrl);
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay with source = STANDALONE_API")
    @Feature("InitiatePayV5")
    @Test(priority = 7, groups = {"transaction", "regression", "sanity"})
    public static void initiateV5LPSourceStandaloneAPIRedirectFlow() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID4);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("isRedirectFlow", "true");
        request.put("returnUrl", TransactionData.RETURN_URL);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertNotNull(MakeTransaction.initiatePojo.checkoutPageUrl);
        Assert.assertNotNull(MakeTransaction.initiatePojo.customParams);
    }

    @Description("To data types of initiate v5 response when source is STANDALONE_API")
    @Feature("InitiatePayV5")
    @Test(priority = 8, groups = {"transaction", "regression", "sanity"})
    public void initiatev5DataTypeSourceStandaloneAPIRedirectFlow() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.paymentModes);
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.checkoutPageUrl.getClass().getSimpleName(), String.class.getSimpleName());
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay with source = STANDALONE_REDIRECT")
    @Feature("InitiatePayV5")
    @Test(priority = 9, groups = {"transaction", "regression", "sanity"})
    public static void initiateV5LPSourceStandaloneRedirect() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID5);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(1), "AUTO_DEBIT");
    }

    @Description("To data types of initiate v5 response when source is STANDALONE_REDIRECT")
    @Feature("InitiatePayV5")
    @Test(priority = 10, groups = {"transaction", "regression", "sanity"})
    public void initiatev5DataTypeSourceStandaloneRedirect() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.checkoutPageUrl);
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay with source = STANDALONE_REDIRECT")
    @Feature("InitiatePayV5")
    @Test(priority = 11, groups = {"transaction", "regression", "sanity"})
    public static void initiateV5LPSourceStandaloneRedirectRedirectFlow() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID6);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("isRedirectFlow", "true");
        request.put("returnUrl", TransactionData.RETURN_URL);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertNotNull(MakeTransaction.initiatePojo.checkoutPageUrl);
        Assert.assertNotNull(MakeTransaction.initiatePojo.customParams);
    }

    @Description("To data types of initiate v5 response when source is STANDALONE_REDIRECT")
    @Feature("InitiatePayV5")
    @Test(priority = 12, groups = {"transaction", "regression", "sanity"})
    public void initiatev5DataTypeSourceStandaloneRedirectRedirectFlow() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.paymentModes);
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.checkoutPageUrl.getClass().getSimpleName(), String.class.getSimpleName());
    }

    @Description("Verify a valid user is able to initiate a BNPL transaction without passing the user's email address for merchant not requiring email address using the v5 version of initiate pay")
    @Feature("InitiatePayV5")
    @Test(priority = 13, groups = { "transaction", "regression", "sanity"})
    public void initiateV5LPWithoutEmail() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("Verify that a blocked user should not be able to initiate a BNPL transaction using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 14, groups = { "transaction", "regression"})
    public void initiateV5LPBlockedUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLOCKED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_BLOCKED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! Your LazyPay account seems to be blocked");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an locked user should not be able to initiate a BNPL transaction using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 15, groups = { "transaction", "regression"})
    public void initiateV5LPLockedUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOCKED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_LOCKED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! Your account seems to be locked");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that a fraud user should not be able to initiate a BNPL transaction using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 16, groups = { "transaction", "regression"})
    public void initiateV5LPBlackListedUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLACKLISTED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_BLACKLISTED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! Your LazyPay account seems to be suspended");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction is initiate of amount greater than user's credit limit using the initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 17, groups = { "transaction", "regression", "sanity"})
    public void initiateV5LPForTxnAmtGreaterThanLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT_HIGH);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_ELIGIBILITY_FAILURE_MAX_SUBMERCHANT_CAP_REACHED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Eligibility declined. Transaction not permitted. Potential Fraud Risk");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction is initiate of amount greater than user's available credit limit using the initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 18, groups = { "transaction", "regression"})
    public void initiateV5LP_moreThanAvailableCreditLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_MAXED_OUT);
        request.put("amount", String.valueOf(EligibilityV0Tests.availableLimitMaxedOutUser +1));
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_EXCEEDS_USER_MAX_TXN_LIMIT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! The transaction value seems to be higher than your available LazyPay credit limit");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction equal to or greater than 9999 is initiated using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 19, groups = { "transaction", "regression", "sanity"})
    public void initiateV5LPMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_ELIGIBILITY_FAILURE_MAX_SUBMERCHANT_CAP_REACHED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Eligibility declined. Transaction not permitted. Potential Fraud Risk");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v5 version of initiate pay")
    @Feature("InitiatePayV5")
    @Test(priority = 20, groups = { "transaction", "regression"})
    public void initiateV5LPDuplicateMTX() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        request.put("MTX", MakeTransaction.MTX);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_DUPLICATE_TRANSACTION_REQUEST");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if a BNPL transaction is initiated for an invalid merchant using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 21, groups = { "transaction", "regression"})
    public void initiateV5LPForInvalidMerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_INVALID);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"401");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_ACCESS_KEY");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Unauthorized");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify a user should not be able to initiate a transaction when source is SOURCE_CITRUS_SSLV2 and CTX is not passed in the initiate v5 API request")
    @Feature("InitiatePayV5")
    @Test(priority = 22, groups = { "transaction", "regression"})
    public void initiateV5LPMandatoryCTXErrorSourceCitrusSSLV2() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("sendCTX", "0");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"CTX_ABSENT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify a user should not be able to initiate a transaction merchant access key is not passed in the initiate v5 API request")
    @Feature("InitiatePayV5")
    @Test(priority = 23, groups = { "transaction", "regression"})
    public void initiateV5LPMandatoryMerchantAccessKeyError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", " ");
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"401");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_ACCESS_KEY");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Unauthorized");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify a user should not be able to initiate a transaction of zero amount using initiate v5 API request")
    @Feature("InitiatePayV5")
    @Test(priority = 24, groups = { "transaction", "regression", "sanity"})
    public void initiateV5LPZeroAmountError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", "0.00");
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_AMOUNT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify a user with fraud email address domain should not be able to initiate a transaction using initiate v5 API request")
    @Feature("InitiatePayV5")
    @Test(priority = 25, groups = { "transaction", "regression"})
    public void initiateV5LPFraudDomainError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL_FRAUD);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_FD_OLD");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's email an error should be thrown if email is not passed in the initiate v5 request")
    @Feature("InitiatePayV5")
    @Test(priority = 26, groups = { "transaction", "regression"})
    public void initiateV5LPWithoutEmailError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if an incorrect amount is passed in the initiate v5 request")
    @Feature("InitiatePayV5")
    @Test(priority = 27, groups = {"transaction", "regression"})
    public void initiateV5LPInvalidAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", "abc!@3");
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_AMOUNT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if signature is not passed in the initiate v5 request header")
    @Feature("InitiatePayV5")
    @Test(priority = 28, groups = { "transaction", "regression"})
    public void initiateV5LPWithoutSignature() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("signature", " ");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_SIGNATURE_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if an incorrect signature is passed in the initiate v5 request header")
    @Feature("InitiatePayV5")
    @Test(priority = 29, groups = {"transaction", "regression"})
    public void initiateV5LPInvalidSignature() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("signature", "InvalidSignature");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"401");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_SIGNATURE_MISMATCH");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Unauthorized");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify mandatory validation on mobile number for the initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 30, groups = { "transaction", "regression"})
    public void initiateV5LPMandatoryMobileError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify the validation that mobile number should be equal to 10 digits on the initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 31, groups = { "transaction", "regression"})
    public void initiateV5LPMobileNotEqual10Digit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "123456789");
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_MOBILE");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Entered mobile number seems to be invalid");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify the validation that mobile number should only be integers on initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 32, groups = { "transaction", "regression"})
    public void initiateV5LPMobileOnlyIntegers() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "Test123!@#");
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_MOBILE");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Entered mobile number seems to be invalid");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's first name an error should be thrown if first name is not passed in the initiate v5 request")
    @Feature("InitiatePayV5")
    @Test(priority = 33, groups = { "transaction", "regression"})
    public void initiateV5LPWithoutFirstName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("lastName", TransactionData.LAST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's last name an error should be thrown if last name is not passed in the initiate v5 request")
    @Feature("InitiatePayV5")
    @Test(priority = 34, groups = { "transaction", "regression"})
    public void initiateV5LPWithoutLastName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's first and last name a BNPL transaction should be initiated when both the parameters are passed in the initiate v5 request")
    @Feature("InitiatePayV5")
    @Test(priority = 35, groups = { "transaction", "regression"})
    public void initiateV5LPWithFirstLastName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("Verify that an error is not thrown if a transaction less than max transaction limit is initiated using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 36, groups = {"transaction", "regression", "sanity"})
    public void initiateV5LPLessThanMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("Verify that error is thrown in initiate pay v5 for invalid sub merchantID")
    @Feature("InitiatePayV5")
    @Test(priority = 37,groups = {"transaction", "regression"})
    public void initiateV5ForInvalidSubMerchantID() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.INVALID_SUBMERCHANT_ID);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_INVALID_SUBMERCHANT_ID");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Sub-merchant ID is not present on the LP system");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that error is thrown in initiate pay v5 for blank sub merchantID")
    @Feature("InitiatePayV5")
    @Test(priority = 38,groups = {"transaction","sanity", "regression"})
    public void initiateV5ForBlankSubMerchantID() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", " ");
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_BLANK_SUB_MERCHANT_ID");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Sub-merchant ID cannot be null or empty");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that error is thrown in initiate pay v5 for no sub merchantID")
    @Feature("InitiatePayV5")
    @Test(priority = 39,groups = {"transaction", "regression"})
    public void initiateV5ForMissingSubMerchantID() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.LAST_NAME);
        request.put("sendSubMerchantId", "false");
        
        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_MISSING_SUB_MERCHANT_ID");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Sub-merchant ID missing in the received request");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that error is thrown in initiate pay v5 for disabled sub merchant")
    @Feature("InitiatePayV5")
    @Test(priority = 40,groups = {"transaction", "regression"})
    public void initiateV5ForDisabledSubMerchantID() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_DISABLED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_SUB_MERCHANT_DISABLED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Sub-merchant is disabled on the LP system");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that error is thrown in initiate pay v5 for invalid sub merchant txnID")
    @Feature("InitiatePayV5")
    @Test(priority = 41,groups = {"transaction","sanity", "regression"})
    public void initiateV5ForBlankSubMerchantTxnID() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_DISABLED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);
        request.put("subMerchantTxnId", " ");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_BLANK_SUB_MERCHANT_TXN_ID");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Sub-merchant txn ID cannot be null or empty");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that error is thrown in initiate pay v5 for missing sub merchant txnID")
    @Feature("InitiatePayV5")
    @Test(priority = 42,groups = {"transaction", "regression"})
    public void initiateV5ForMissingSubMerchantTxnID() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_DISABLED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);
        request.put("sendSubMerchantTxnId", "false");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_MISSING_SUB_MERCHANT_TXN_ID");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Sub-merchant txn ID missing in the received request");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction equal to or greater than 10000 is initiated using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 43,groups = {"transaction", "regression", "sanity"})
    public void initiateV5LPMaxTxnLimitExcludeCap() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EXCLUDE_CAP);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_EXCLUDE_CAP);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("Verify that an error is thrown if a transaction equal to or greater than 10000 is initiated using initiate v5 API")
    @Feature("InitiatePayV5")
    @Test(priority = 44,groups = {"transaction", "regression", "sanity"})
    public void initiateV5LPMaxTxnLimitExcludeCapAppliesDirectCap() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EXCLUDE_CAP);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_EXCLUDE_CAP);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_PER_TXN_MAX_LIMIT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, TransactionData.MAX_TXN_AMOUNT_ERROR_MSG);
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v5/payment/initiate");
    }
}