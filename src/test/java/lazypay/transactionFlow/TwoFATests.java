package lazypay.transactionFlow;

import deviceInfo.DeviceInfo;
import deviceInfo.DeviceInfoData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.userplatform.UserPlatformSupportData;

import java.util.HashMap;
import static deviceInfo.DeviceInfo.checkExistenceAPIResponsePojo;
import static deviceInfo.DeviceInfoData.*;
import static lazypay.MakeTransaction.*;
import static lazypay.transactionFlow.GetOTPID.getAppOTPPojo;
import static lazypay.transactionFlow.TransactionData.*;

public class TwoFATests extends UserPlatformSupportData  {

    public TwoFATests() throws Exception {
        super();
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v0 and pay v0 for a valid user")
    @Feature("2FA")
    @Test(priority = 1, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFATransactionInitiateV0PayV0() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID16, "", INITIATE_PAY_V0, PAY_V0, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v0 and pay v4 for a valid user")
    @Feature("2FA")
    @Test(priority = 2, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFATransactionInitiateV0PayV4() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID17, "", INITIATE_PAY_V0, PAY_V4, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v1 and pay v0 for a valid user")
    @Feature("2FA")
    @Test(priority = 3, groups = {"2FA", "transaction", "regression"})
    public void TwoFATransactionInitiateV1PayV0() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID18, "", INITIATE_PAY_V1, PAY_V0, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v1 and pay v4 for a valid user")
    @Feature("2FA")
    @Test(priority = 4, groups = {"2FA", "transaction", "regression"})
    public void TwoFATransactionInitiateV1PayV4() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID19, "", INITIATE_PAY_V1, PAY_V4, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v2 and pay v0 for a valid user")
    @Feature("2FA")
    @Test(priority = 5, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFATransactionInitiateV2PayV0() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID20, "", INITIATE_PAY_V2, PAY_V0, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v2 and pay v4 for a valid user")
    @Feature("2FA")
    @Test(priority = 6, groups = {"2FA", "transaction", "regression"})
    public void TwoFATransactionInitiateV2PayV4() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID16, "", INITIATE_PAY_V2, PAY_V4, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v4 and pay v0 for a valid user")
    @Feature("2FA")
    @Test(priority = 7, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFATransactionInitiateV4PayV0() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID17, "", INITIATE_PAY_V4, PAY_V0, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v4 and pay v4 for a valid user")
    @Feature("2FA")
    @Test(priority = 8, groups = {"2FA", "transaction", "regression"})
    public void TwoFATransactionInitiateV4PayV4() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID18, "", INITIATE_PAY_V4, PAY_V4, ACCESS_KEY_2FA, TRANSACTION_AMOUNT, "", true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a 2FA transaction is proceeded successfully using initiate v5 and pay v5 for a valid user")
    @Feature("2FA")
    @Test(priority = 9, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFATransactionInitiateV5PayV5() throws Exception {
        MakeTransaction.makeLPOTPTransaction(USER_MOBILE_VALID19, "", INITIATE_PAY_V5, PAY_V5, ACCESS_KEY_2FA_AGG, TRANSACTION_AMOUNT, SUB_MERCHANT_ID_2FA, true);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify that the app-otp API should not return OTP details if transaction is not 2FA")
    @Feature("2FA")
    @Test(priority = 10, groups = {"2FA", "transaction", "regression", "sanity"})
    public void AppOTPNotToReturnNon2FATxnOTPID() throws Exception {
        initiateLPOTPTransaction(USER_MOBILE_VALID20, "", INITIATE_PAY_V0, ACCESS_KEY, TRANSACTION_AMOUNT, "");
        Assert.assertNotNull(initiatePojo.txnRefNo);
        Assert.assertNotNull(initiatePojo.lpTxnId);

        GetOTPID.getAppOTPMethod(null, initiatePojo.lpTxnId);
        Assert.assertEquals(getAppOTPPojo.status, "400");
        Assert.assertEquals(getAppOTPPojo.error, "Bad Request");
        Assert.assertEquals(getAppOTPPojo.message, "Provided txn is not a 2FA txn");
        Assert.assertEquals(getAppOTPPojo.errorCode, "LP_2FA_TXN_NOT_FOUND");
    }

    @Description("To verify that the OTP for an expired 2FA transaction is not return in the app-otp API")
    @Feature("2FA")
    @Test(priority = 11, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAExpiredTransactionID() throws Exception {
        GetOTPID.getAppOTPMethod(null, EXPIRED_TRANSACTION);
        Assert.assertEquals(getAppOTPPojo.status, "400");
        Assert.assertEquals(getAppOTPPojo.error, "Bad Request");
        Assert.assertEquals(getAppOTPPojo.message, "2FA sale transaction is not in Initiated state");
        Assert.assertEquals(getAppOTPPojo.errorCode, "LP_2FA_TXN_EXPIRED");
    }

    @Description("To verify that app-otp API throws and error for an incorrect transaction ID")
    @Feature("2FA")
    @Test(priority = 12, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAIncorrectTransactionID() throws Exception {
        GetOTPID.getAppOTPMethod(null, "DummyTxnID");
        Assert.assertEquals(getAppOTPPojo.status, "400");
        Assert.assertEquals(getAppOTPPojo.error, "Bad Request");
        Assert.assertEquals(getAppOTPPojo.message, "Transaction not found");
    }

    @Description("To verify a 2FA transaction with device rule enable merchant is proceeded successfully using initiate v0 and pay v0 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 13, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV0PayV0TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID21);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID21_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED2);
        Thread.sleep(2000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "PLATFORM"),PLATFORM_NAME_ANDROID, "Platform name not saved in txnCustomParamsTable");
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "DEVICE_ID"),TRUSTED_DEVICE1, "Device ID not saved in txnCustomParamsTable");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v0 and pay v4 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 14, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV0PayV4TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID22);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID22_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v2 and pay v4 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 15, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV2PayV4TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID23);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V2);
        request.put("lpUserId",USER_MOBILE_VALID23_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v1 and pay v0 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 16, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV1PayV0TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID24);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V1);
        request.put("lpUserId",USER_MOBILE_VALID24_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v1 and pay v4 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 17, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV1PayV4TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID25);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V1);
        request.put("lpUserId",USER_MOBILE_VALID25_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v2 and pay v0 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 18, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV2PayV0TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID26);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V2);
        request.put("policyId", POLICYID3);
        request.put("lpUserId",USER_MOBILE_VALID26_LPUSERID);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v4 and pay v0 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 19, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV4PayV0TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID27);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID27_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v4 and pay v4 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 20, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV4PayV4TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID28);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID28_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        //Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant aggregator using initiate v5 and pay v5 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 21, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV5PayV5TrustedDeviceMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID29);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("lpUserId",USER_MOBILE_VALID29_LPUSERID);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable submerchant aggregator using initiate v5 and pay v5 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 22, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV5PayV5TrustedDeviceSMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID30);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_SMAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule3);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("lpUserId",USER_MOBILE_VALID30_LPUSERID);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Trusted Device is not present in deviceinfo table");
        long lastStoredAtTimeBeforeTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        long lastStoredAtTimeAfterTxn = (long) checkExistenceAPIResponsePojo.deviceFetchDetails.get("lastStoredAt");
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertTrue(lastStoredAtTimeBeforeTxn<lastStoredAtTimeAfterTxn, "lastStoredAtTime updated after txn");
    }
    @Description("To verify a 2FA transaction with device rule enable merchant is proceeded successfully using initiate v0 and pay v0 for a valid user and non trusted device")
    @Feature("2FA")
    @Test(priority = 23, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV0PayV0UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID21);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE3);
        request.put("platform",PLATFORM_NAME_WEB);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID21_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in deviceinfo table");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v0 and pay v4 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 24, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV0PayV4UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID22);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE4);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID22_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v2 and pay v4 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 25, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV2PayV4UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID23);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE12);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V2);
        request.put("lpUserId",USER_MOBILE_VALID23_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v1 and pay v0 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 26, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV1PayV0UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID24);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE5);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V1);
        request.put("lpUserId",USER_MOBILE_VALID24_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v1 and pay v4 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 27, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV1PayV4UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID25);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE6);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V1);
        request.put("lpUserId",USER_MOBILE_VALID25_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(1000);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v2 and pay v0 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 28, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV2PayV0UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID26);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE7);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V2);
        request.put("lpUserId",USER_MOBILE_VALID26_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(500);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v4 and pay v0 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 29, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV4PayV0UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID27);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE11);
        request.put("platform",PLATFORM_NAME_IOS);
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID27_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(500);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant using initiate v4 and pay v4 for a valid user and untrusted device")
    @Feature("2FA")
    @Test(priority = 30, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV4PayV4UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID28);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE10);
        request.put("platform",PLATFORM_NAME_WEB);
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID28_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(500);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in deviceinfo table");
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable merchant aggregator using initiate v5 and pay v5 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 31, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV5PayV5UnTrustedDeviceMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID29);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE8);
        request.put("platform",PLATFORM_NAME_IOS);
        request.put("payVersion", PAY_V5);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("lpUserId",USER_MOBILE_VALID29_LPUSERID);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(500);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
    }

    @Description("To verify a 2FA transaction proceeded successfully for device rule enable submerchant aggregator using initiate v5 and pay v5 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 32, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV5PayV5UnTrustedDeviceSMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID30);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_SMAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE9);
        request.put("platform",PLATFORM_NAME_WEB);
        request.put("payVersion", PAY_V5);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule3);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("lpUserId",USER_MOBILE_VALID30_LPUSERID);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is already present in device info table");
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Thread.sleep(500);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device not created in device info table");
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
    }

    @Description("To verify a AutoDebit transaction proceeded successfully for device rule and 2FA enabled merchant using pay V0 for a trusted device and valid token.Also verify the device details saved in TxnCustomParams Table")
    @Feature("2FA")
    @Test(priority = 33, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV0TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID31);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID31_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        //Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "PLATFORM"),PLATFORM_NAME_ANDROID, "Platform name not saved in txnCustomParamsTable");
        //Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "DEVICE_ID"),TRUSTED_DEVICE1, "Device ID not saved in txnCustomParamsTable");
    }

    @Description("To verify a AutoDebit transaction proceeded successfully for device rule and 2FA enabled merchant using pay V4 for a trusted device and valid token.Also verify the device details saved in TxnCustomParams Table")
    @Feature("2FA")
    @Test(priority = 34, groups = {"2FA", "transaction", "regression"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV4TrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID37);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID37_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Thread.sleep(2000);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "PLATFORM"),PLATFORM_NAME_ANDROID, "Platform name not saved in txnCustomParamsTable");
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "DEVICE_ID"),TRUSTED_DEVICE1, "Device ID not saved in txnCustomParamsTable");
    }

    @Description("To verify a AutoDebit transaction proceeded successfully for device rule and 2FA enabled merchant using pay V5 (Merchant Aggregator) for a trusted device and valid token.Also verify the device details saved in TxnCustomParams Table")
    @Feature("2FA")
    @Test(priority = 35, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV5TrustedDeviceMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID33);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("lpUserId",USER_MOBILE_VALID33_LPUSERID);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Thread.sleep(2000);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "PLATFORM"),PLATFORM_NAME_ANDROID, "Platform name not saved in txnCustomParamsTable");
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "DEVICE_ID"),TRUSTED_DEVICE1, "Device ID not saved in txnCustomParamsTable");
    }

    @Description("To verify a AutoDebit transaction successful for device rule and 2FA enabled merchant using pay V5 (SubMerchant Aggregator) for a trusted device and valid token. Also verify the device details saved in TxnCustomParams Table")
    @Feature("2FA")
    @Test(priority = 36, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV5TrustedDeviceSMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID34);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_SMAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("lpUserId",USER_MOBILE_VALID34_LPUSERID);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule3);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Thread.sleep(2000);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "PLATFORM"),PLATFORM_NAME_ANDROID, "Platform name not saved in txnCustomParamsTable");
        Assert.assertEquals(getAdditionalParamsValueFromCustomParam(payPojo.transactionId, "DEVICE_ID"),TRUSTED_DEVICE1, "Device ID not saved in txnCustomParamsTable");
    }

    @Description("To verify a AutoDebit transaction failed for device rule and 2FA enabled merchant using pay V0 for Untrusted device and valid token")
    @Feature("2FA")
    @Test(priority = 37, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV0UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID37);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE2);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID37_LPUSERID);
        request.put("policyId", POLICYID2);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify a AutoDebit transaction failed for device rule and 2FA enabled merchant using pay V4 for Untrusted device and valid token")
    @Feature("2FA")
    @Test(priority = 38, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV4UnTrustedDevice() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID42);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID42_LPUSERID);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify a AutoDebit transaction failed for device rule and 2FA enabled merchant using pay V5 (Merchant Aggregator) for Untrusted device and valid token")
    @Feature("2FA")
    @Test(priority = 39, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV5UnTrustedDeviceMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID43);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("lpUserId",USER_MOBILE_VALID43_LPUSERID);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("policyId", POLICYID1);
        request.put("authToken", AUTH_TOKEN_USER_MOBILE_VALID43_ACCESS_KEY_2FA_DEVICERULE_MAgg);
        DeviceInfoData.deleteDeviceDataFromDeviceInfo(request);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify a AutoDebit transaction failed for device rule and 2FA enabled merchant using pay V5 (SubMerchant Aggregator) for a Untrusted device and valid token")
    @Feature("2FA")
    @Test(priority = 40, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV5UnTrustedDeviceSMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID43);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_SMAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("lpUserId",USER_MOBILE_VALID43_LPUSERID);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule3);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("policyId", POLICYID1);
        request.put("authToken", AUTH_TOKEN_USER_MOBILE_VALID43_ACCESS_KEY_2FA_DEVICERULE_SMAgg);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertFalse(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify a New User AutoDebit transaction Successfull for device rule and 2FA enabled merchant using pay V0 for Untrusted device and Valid token (Token Generated through account link API)")
    @Feature("2FA")
    @Test(priority = 41, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV0UnTrustedDeviceNewUser() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID38_NEWUSER);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_WEB);
        request.put("payVersion", PAY_V0);
        request.put("policyId", POLICYID1);
        request.put("lpUserId",USER_MOBILE_VALID38_LPUSERID);
        request.put("user_id",USER_MOBILE_VALID38_LPUSERID);
        request.put("userId",USER_MOBILE_VALID38_LPUSERID);
        TransactionData.deleteUserAccount(request);
        TransactionData.deleteUserFromUsersTable(request);
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a New User AutoDebit transaction Successfull for device rule and 2FA enabled merchant using pay V4 for Untrusted device and Valid token (Token Generated through account link API)")
    @Feature("2FA")
    @Test(priority = 42, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV4UnTrustedDeviceNewUser() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID39_NEWUSER);
        request.put("amount", TRANSACTION_AMOUNT2);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",UNTRUSTED_DEVICE2);
        request.put("platform",PLATFORM_NAME_WEB);
        request.put("payVersion", PAY_V4);
        request.put("policyId", POLICYID1);
        request.put("user_id",USER_MOBILE_VALID39_LPUSERID);
        request.put("userId",USER_MOBILE_VALID39_LPUSERID);
        TransactionData.deleteUserAccount(request);
        TransactionData.deleteUserFromUsersTable(request);
        Thread.sleep(1000);
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify a AutoDebit transaction failed for device rule and 2FA enabled merchant using pay V0 for trusted device and valid token but txnAmount>maxTxnAmount")
    @Feature("2FA")
    @Test(priority = 43, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV0TrustedMaxTxnAmount() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID31);
        request.put("amount", TRANSACTION_AMOUNT5);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V0);
        request.put("lpUserId",USER_MOBILE_VALID31_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify a AutoDebit transaction failed for device rule and 2FA enabled merchant using pay V4 for a trusted device and valid token but txnAmount>maxTxnAmount")
    @Feature("2FA")
    @Test(priority = 44, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV4TrustedMaxTxnAmount() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID44);
        request.put("amount", TRANSACTION_AMOUNT5);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V4);
        request.put("lpUserId",USER_MOBILE_VALID44_LPUSERID);
        request.put("policyId", POLICYID3);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify a AutoDebit transaction proceeded successfully for device rule and 2FA enabled merchant using pay V5 (Merchant Aggregator) for a trusted device and valid token but txnAmount>maxTxnAmount")
    @Feature("2FA")
    @Test(priority = 45, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFADeviceRuleAutoDebitTransactionPayV5TrustedMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID33);
        request.put("amount", TRANSACTION_AMOUNT5);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("deviceId",TRUSTED_DEVICE1);
        request.put("platform",PLATFORM_NAME_ANDROID);
        request.put("payVersion", PAY_V5);
        request.put("lpUserId",USER_MOBILE_VALID33_LPUSERID);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("policyId", POLICYID1);
        DeviceInfo.verifyDeviceInDeviceInfoTable(request);
        Assert.assertTrue(checkExistenceAPIResponsePojo.isDevicePresent, "Device is not present in device info table");
        MakeTransaction.makeLPAutoDebitTransactionHash(request);
        Assert.assertEquals(payPojo.status, "401");
        Assert.assertEquals(payPojo.message, "Oops!! Additional Authentication required for given transaction", " It is not failed due to 2FA required. Failed reason is something else");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify the deviceID and platform is non mandatory even for device rule enable merchant using initiate v0 and pay v0 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 46, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV0PayV0() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID35);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("payVersion", PAY_V0);
        request.put("initiateVersion", INITIATE_PAY_V0);
        request.put("policyId", POLICYID1);
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify the deviceID and platform is non mandatory even for device rule enable merchant using initiate v2 and pay v4 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 47, groups = {"2FA", "transaction", "regression"})
    public void TwoFAWithDeviceRuleTransactionInitiateV2PayV4() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID36);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("payVersion", PAY_V4);
        request.put("initiateVersion", INITIATE_PAY_V2);
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }

    @Description("To verify the devicID and platform is non mandatory even for device rule enable merchant using initiate v5 and pay v5 for a valid user and trusted device")
    @Feature("2FA")
    @Test(priority = 48, groups = {"2FA", "transaction", "regression", "sanity"})
    public void TwoFAWithDeviceRuleTransactionInitiateV5PayV5() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID36);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("is2FA", "true");
        request.put("payVersion", PAY_V5);
        request.put("initiateVersion", INITIATE_PAY_V5);
        request.put("subMerchantId", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("subMerchantTxnId", SUB_MERCHANT_TXN_ID);
        MakeTransaction.makeLPOTPTransactionHash(request, MAX_RETRIES_ALLOWED);
        Assert.assertNotNull(payPojo.transactionId);
        Assert.assertNotNull(payPojo.amount);
    }
}