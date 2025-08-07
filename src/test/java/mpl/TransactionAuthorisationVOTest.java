package mpl;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TransactionAuthorisationVOTest {

    @Description("Verify that valid user is able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 1, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void eligibleExistingUserTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.transactionId);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, TestData.TRANSACTION_STATUS_AUTHORISED);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.amount, TestData.TRANSACTION_AMOUNT_DEFAULT);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, TestData.TRANSACTION_MESSAGE_AUTHORISED);
        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.customParams);
    }

    @Description("Verify that response data type for v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 2, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public void responseDataTypeTxnAuthV0() {

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.transactionId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.amount.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
    }

    @Description("Verify that valid user is able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 3, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void eligibleNewUserTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_NEW_TXN);
        request.put("email", TestData.USER_EMAIL_NEW_TXN);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.transactionId);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, TestData.TRANSACTION_STATUS_AUTHORISED);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.amount, TestData.TRANSACTION_AMOUNT_DEFAULT);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, TestData.TRANSACTION_MESSAGE_AUTHORISED);
        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.customParams);
        Assert.assertNotNull(TransactionData.getAccountDetails(TestData.USER_MOBILE_NEW_TXN, TestData.TENANT_MEESHO,"account_id"));
        Assert.assertEquals(TransactionData.getAccountDetails(TestData.USER_MOBILE_NEW_TXN, TestData.TENANT_MEESHO,"status"), "ACTIVE");
    }

    @Description("Verify that a late fee blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 4, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void lateFeeBlockedUserTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_BLOCKED);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "403", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Your Mpl account seems to be blocked.", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_USER_BLOCKED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a OTP blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 5, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void otpBockedUserTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_LOCKED);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "403", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Your Mpl account seems to be blocked.", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_USER_BLOCKED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 6, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void fraudBlockedUserTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_BLACKLISTED);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "403", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Your Mpl account seems to be suspended", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_USER_BLACKLISTED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 7, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void inEligibleUserTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_INELIGIBLE);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "409", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Conflict", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! We don't seem to have Mpl open for you as of now. But we promise to throw it open to you soon", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_USER_INELIGIBLE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 8, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void existingLazypayUserMeeshoTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "409", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Conflict", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Sign up is required", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_SIGNUP_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 9, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void existingMeeshoUserLazypayBNPLOTPTxn() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V0);
        request.put("payVersion", TransactionData.PAY_V0);

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId,"verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.payPojo.amount, TransactionData.TRANSACTION_AMOUNT);
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 10, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void existingMeeshoUserLazypayBNPLAutoDebitTxn() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("payVersion", TransactionData.PAY_V0);

        MakeTransaction.makeLPAutoDebitTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId, "verify that user is valid and transaction data is correct");
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId, MakeTransaction.MTX);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 11, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void greaterThanUserLimitTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("amount", TestData.TRANSACTION_AMOUNT_HIGH);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.transactionId);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "FAIL", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Transaction is failed", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.amount, TestData.TRANSACTION_AMOUNT_HIGH);
        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.customParams);
    }

    //@Test verifyEligibilityV0TxnGTBalance

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 13, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void greaterThanMaxTxnLimitTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", String.valueOf(Double.valueOf(TestData.TRANSACTION_AMOUNT_MAX)+0.1));
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "409", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Conflict", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Merchant transaction limit exceeded", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_EXCEEDS_MAX_TXN_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 14, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void equalToMaxTxnLimitTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_MAX);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.transactionId);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, TestData.TRANSACTION_STATUS_AUTHORISED);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.amount, TestData.TRANSACTION_AMOUNT_MAX);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, TestData.TRANSACTION_MESSAGE_AUTHORISED);
        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.customParams);
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 15, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void lessThanMaxTxnLimitTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", String.valueOf(Double.valueOf(TestData.TRANSACTION_AMOUNT_MAX)-0.1));
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.transactionId);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, TestData.TRANSACTION_STATUS_AUTHORISED);
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.amount, String.valueOf(Double.valueOf(TestData.TRANSACTION_AMOUNT_MAX)-0.1));
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, TestData.TRANSACTION_MESSAGE_AUTHORISED);
        Assert.assertNotNull(TransactionAuthorisationStep.transactionAuthorisationPOJO.customParams);
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 16, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void duplicateMTXTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("merchantTxnId", TestData.EXISTING_MTX);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "409", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Conflict", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Mpl duplicate transaction request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_DUPLICATE_TRANSACTION_REQUEST", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 17, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void invalidMerchantAccessKeyTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TransactionData.ACCESS_KEY_INVALID);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! You do not seem to have access", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_ACCESS_DENIED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 18, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void mandatoryMerchantAccessKeyValidationTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Access key is required", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_ACCESS_KEY_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 19, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void zeroAmountTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", "0.0");
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 20, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void lessThanOneAmountTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", "0.23");
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 21, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void negativeAmountTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", "-1.23");
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 22, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void invalidAmountTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", "InvalidAmount");
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 23, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void mandatorySignatureValidationTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("signature", " ");
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Signature is missing", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_SIGNATURE_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 24, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void invalidSignatureTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("signature", "InvalidSignature");
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "There is mismatch in the signature", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_SIGNATURE_MISMATCH", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 25, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void mandatoryMobileValidationTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Mandatory user name parameters required", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 26, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void invalidMobileTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "98765432101");
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that a fraud blocked user is not able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 27, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void invalidMobileFormatTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "123abc@#$");
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "MPL_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that valid user is able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 28, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void customParamMaxLimitTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_1);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("customParamMaxLimit", String.valueOf(true));
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "CUSTOM_PARAMS_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("Verify that valid user is able to authenticate a Messho BNPL transaction using the v0 transaction/authorisation API")
    @Feature("TransactionAuthV0")
    @Test(priority = 29, groups = {"transaction", "regression", "sanity", "mpl_regression"})
    public static void customParamMaxKeyLengthTxnAuthV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_1);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_DEFAULT);
        request.put("street1", TestData.STREET_1);
        request.put("street2", TestData.STREET_2);
        request.put("city", TestData.CITY);
        request.put("state", TestData.STATE);
        request.put("country", TestData.COUNTRY);
        request.put("zip", TestData.ZIP);
        request.put("landmark", TestData.LANDMARK);
        request.put("residenceType", TestData.RESIDENCE_TYPE);
        request.put("deviceId", TestData.DEVICE_ID);
        request.put("platform", TestData.PLATFORM_WEB);
        request.put("userIpAddress", TestData.USER_IP_ADDRESS);
        request.put("customParamMaxLength", String.valueOf(true));
        request.put("version", TransactionData.INITIATE_PAY_V0);

        TransactionAuthorisationStep.transactionAuthorisationMethod(request);

        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(TransactionAuthorisationStep.transactionAuthorisationPOJO.errorCode, "CUSTOM_PARAMS_KEY_LENGTH_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }
}
