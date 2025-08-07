package lazypay.transactionFlow;

import api.lazypay.transaction.InitiateV4;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class InitiatePayV4Tests {

    public InitiatePayV4Tests() {
    }

    public static String signature;
    static public String MTX;
    static InitiateV4 initiateV4;

    static {
        try {
            initiateV4 = new InitiateV4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay with source = CITRUS_SDK")
    @Feature("InitiatePayV4")
    @Test(priority = 1, groups = {"transaction", "regression", "sanity"})
    public static void initiatev4LPSourceCitrusSDK() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("To data types of initiate v4 response when source is CITRUS_SDK")
    @Feature("InitiatePayV4")
    @Test(priority = 2, groups = {"transaction", "regression", "sanity"})
    public void initiatev4DataTypeSourceCitrusSDK() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay with source = CITRUS_SSLV2")
    @Feature("InitiatePayV4")
    @Test(priority = 3, groups = {"transaction", "regression", "sanity"})
    public static void initiatev4LPSourceCitrusSSLV2() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID2);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("To data types of initiate v4 response when source is CITRUS_SSLV2")
    @Feature("InitiatePayV4")
    @Test(priority = 4, groups = {"transaction", "regression", "sanity"})
    public void initiatev4DataTypeSourceCitrusSSLV2() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay with source = STANDALONE_API")
    @Feature("InitiatePayV4")
    @Test(priority = 5, groups = {"transaction", "regression", "sanity"})
    public static void initiatev4LPSourceStandaloneAPI() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(1), "AUTO_DEBIT");
    }

    @Description("To data types of initiate v4 response when source is STANDALONE_API")
    @Feature("InitiatePayV4")
    @Test(priority = 6, groups = {"transaction", "regression", "sanity"})
    public void initiatev4DataTypeSourceStandaloneAPI() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.checkoutPageUrl);
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay with source = STANDALONE_API")
    @Feature("InitiatePayV4")
    @Test(priority = 7, groups = {"transaction", "regression", "sanity"})
    public static void initiatev4LPSourceStandaloneAPIRedirectFlow() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID4);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("isRedirectFlow", "true");
        request.put("returnUrl", TransactionData.RETURN_URL);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertNotNull(MakeTransaction.initiatePojo.checkoutPageUrl);
        Assert.assertNotNull(MakeTransaction.initiatePojo.customParams);
    }

    @Description("To data types of initiate v4 response when source is STANDALONE_API")
    @Feature("InitiatePayV4")
    @Test(priority = 8, groups = {"transaction", "regression", "sanity"})
    public void initiatev4DataTypeSourceStandaloneAPIRedirectFlow() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.paymentModes);
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.checkoutPageUrl.getClass().getSimpleName(), String.class.getSimpleName());
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay with source = STANDALONE_REDIRECT")
    @Feature("InitiatePayV4")
    @Test(priority = 9, groups = {"transaction", "regression", "sanity"})
    public static void initiatev4LPSourceStandaloneRedirect() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID5);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(1), "AUTO_DEBIT");
    }

    @Description("To data types of initiate v4 response when source is STANDALONE_REDIRECT")
    @Feature("InitiatePayV4")
    @Test(priority = 10, groups = {"transaction", "regression", "sanity"})
    public void initiatev4DataTypeSourceStandaloneRedirect() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.checkoutPageUrl);
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay with source = STANDALONE_REDIRECT")
    @Feature("InitiatePayV4")
    @Test(priority = 11, groups = {"transaction", "regression", "sanity"})
    public static void initiatev4LPSourceStandaloneRedirectRedirectFlow() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID6);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("isRedirectFlow", "true");
        request.put("returnUrl", TransactionData.RETURN_URL);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertNotNull(MakeTransaction.initiatePojo.checkoutPageUrl);
        Assert.assertNotNull(MakeTransaction.initiatePojo.customParams);
    }

    @Description("To data types of initiate v4 response when source is STANDALONE_REDIRECT")
    @Feature("InitiatePayV4")
    @Test(priority = 12, groups = {"transaction", "regression", "sanity"})
    public void initiatev4DataTypeSourceStandaloneRedirectRedirectFlow() {

        Assert.assertEquals(MakeTransaction.initiatePojo.txnRefNo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertNull(MakeTransaction.initiatePojo.paymentModes);
        Assert.assertEquals(MakeTransaction.initiatePojo.lpTxnId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.initiatePojo.checkoutPageUrl.getClass().getSimpleName(), String.class.getSimpleName());
    }

    @Description("Verify a valid user is able to initiate a BNPL transaction without passing the user's email address for merchant not requiring email address using the v4 version of initiate pay")
    @Feature("InitiatePayV4")
    @Test(priority = 13, groups = { "transaction", "regression", "sanity"})
    public void initiatev4LPWithoutEmail() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("Verify that a blocked user should not be able to initiate a BNPL transaction using initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 14, groups = { "transaction", "regression"})
    public void initiatev4LPBlockedUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLOCKED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_BLOCKED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! Your LazyPay account seems to be blocked");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an locked user should not be able to initiate a BNPL transaction using initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 15, groups = { "transaction", "regression"})
    public void initiatev4LPLockedUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOCKED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_LOCKED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! Your account seems to be locked");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that a fraud user should not be able to initiate a BNPL transaction using initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 16, groups = { "transaction", "regression"})
    public void initiatev4LPBlackListedUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLACKLISTED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_BLACKLISTED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! Your LazyPay account seems to be suspended");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction is initiate of amount greater than user's credit limit using the initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 17, groups = { "transaction", "regression", "sanity"})
    public void initiatev4LPForTxnAmtGreaterThanLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT_HIGH);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_EXCEEDS_USER_MAX_TXN_LIMIT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! The transaction value seems to be higher than your available LazyPay credit limit");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction is initiate of amount greater than user's available credit limit using the initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 18, groups = { "transaction", "regression"})
    public void initiatev4LP_moreThanAvailableCreditLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_MAXED_OUT);
        request.put("amount", String.valueOf(EligibilityV0Tests.availableLimitMaxedOutUser +1));
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_EXCEEDS_USER_MAX_TXN_LIMIT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message,"Oops!! The transaction value seems to be higher than your available LazyPay credit limit");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if a transaction equal to or greater than 9999 is initiated using initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 19, groups = { "transaction", "regression", "sanity"})
    public void initiatev4LPMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_PER_TXN_MAX_LIMIT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, TransactionData.MAX_TXN_AMOUNT_ERROR_MSG);
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that valid user is able to initiate a BNPL transaction using the v4 version of initiate pay")
    @Feature("InitiatePayV4")
    @Test(priority = 20, groups = { "transaction", "regression"})
    public void initiatev4LPDuplicateMTX() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        request.put("MTX", MakeTransaction.MTX);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_DUPLICATE_TRANSACTION_REQUEST");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if a BNPL transaction is initiated for an invalid merchant using initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 21, groups = { "transaction", "regression"})
    public void initiatev4LPForInvalidMerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_INVALID);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"401");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_ACCESS_KEY");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Unauthorized");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify a user should not be able to initiate a transaction when source is SOURCE_CITRUS_SSLV2 and CTX is not passed in the initiate v4 API request")
    @Feature("InitiatePayV4")
    @Test(priority = 22, groups = { "transaction", "regression"})
    public void initiatev4LPMandatoryCTXErrorSourceCitrusSSLV2() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("sendCTX", "0");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"CTX_ABSENT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify a user should not be able to initiate a transaction merchant access key is not passed in the initiate v4 API request")
    @Feature("InitiatePayV4")
    @Test(priority = 23, groups = { "transaction", "regression"})
    public void initiatev4LPMandatoryMerchantAccessKeyError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", " ");
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"401");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_ACCESS_KEY");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Unauthorized");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify a user should not be able to initiate a transaction of zero amount using initiate v4 API request")
    @Feature("InitiatePayV4")
    @Test(priority = 24, groups = { "transaction", "regression", "sanity"})
    public void initiatev4LPZeroAmountError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", "0.00");
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_AMOUNT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify a user with fraud email address domain should not be able to initiate a transaction using initiate v4 API request")
    @Feature("InitiatePayV4")
    @Test(priority = 25, groups = { "transaction", "regression"})
    public void initiatev4LPFraudDomainError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL_FRAUD);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_FD_OLD");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's email an error should be thrown if email is not passed in the initiate v4 request")
    @Feature("InitiatePayV4")
    @Test(priority = 26, groups = { "transaction", "regression"})
    public void initiatev4LPWithoutEmailError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if an incorrect amount is passed in the initiate v4 request")
    @Feature("InitiatePayV4")
    @Test(priority = 27, groups = {"transaction", "regression"})
    public void initiatev4LPInvalidAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", "abc!@3");
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_AMOUNT");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if signature is not passed in the initiate v4 request header")
    @Feature("InitiatePayV4")
    @Test(priority = 28, groups = { "transaction", "regression"})
    public void initiatev4LPWithoutSignature() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("signature", " ");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_SIGNATURE_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that an error is thrown if an incorrect signature is passed in the initiate v4 request header")
    @Feature("InitiatePayV4")
    @Test(priority = 29, groups = {"transaction", "regression"})
    public void initiatev4LPInvalidSignature() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("signature", "InvalidSignature");

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"401");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_SIGNATURE_MISMATCH");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Unauthorized");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify mandatory validation on mobile number for the initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 30, groups = { "transaction", "regression"})
    public void initiatev4LPMandatoryMobileError() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify the validation that mobile number should be equal to 10 digits on the initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 31, groups = { "transaction", "regression"})
    public void initiatev4LPMobileNotEqual10Digit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "123456789");
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_MOBILE");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Entered mobile number seems to be invalid");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify the validation that mobile number should only be integers on initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 32, groups = { "transaction", "regression"})
    public void initiatev4LPMobileOnlyIntegers() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "Test123!@#");
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status,"400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode,"LP_INVALID_MOBILE");
        Assert.assertEquals(MakeTransaction.initiatePojo.error,"Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Entered mobile number seems to be invalid");
        Assert.assertEquals(MakeTransaction.initiatePojo.path,"/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's first name an error should be thrown if first name is not passed in the initiate v4 request")
    @Feature("InitiatePayV4")
    @Test(priority = 33, groups = { "transaction", "regression"})
    public void initiatev4LPWithoutFirstName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_FNAME_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("lastName", TransactionData.LAST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's last name an error should be thrown if last name is not passed in the initiate v4 request")
    @Feature("InitiatePayV4")
    @Test(priority = 34, groups = { "transaction", "regression"})
    public void initiatev4LPWithoutLastName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_LNAME_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("firstName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertEquals(MakeTransaction.initiatePojo.status, "400");
        Assert.assertEquals(MakeTransaction.initiatePojo.errorCode, "LP_USER_DETAILS_REQUIRED");
        Assert.assertEquals(MakeTransaction.initiatePojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.initiatePojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.initiatePojo.path, "/api/lazypay/v4/payment/initiate");
    }

    @Description("Verify that for a merchant requiring user's first and last name a BNPL transaction should be initiated when both the parameters are passed in the initiate v4 request")
    @Feature("InitiatePayV4")
    @Test(priority = 35, groups = { "transaction", "regression"})
    public void initiatev4LPWithFirstLastName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_LNAME_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }

    @Description("Verify that an error is not thrown if a transaction less than max transaction limit is initiated using initiate v4 API")
    @Feature("InitiatePayV4")
    @Test(priority = 36, groups = {"transaction", "regression", "sanity"})
    public void initiatev4LPLessThanMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V4);
        request.put("firstName", TransactionData.FIRST_NAME);
        request.put("lastName", TransactionData.FIRST_NAME);

        MakeTransaction.initiateLPOTPTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.initiatePojo.txnRefNo);
        Assert.assertNotNull(MakeTransaction.initiatePojo.lpTxnId);
        Assert.assertEquals(MakeTransaction.initiatePojo.paymentModes.get(0), "OTP");
    }
}