package lazypay.transactionFlow;

import api.ams.TokenValidate;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.userplatform.UserPlatformSupportData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static lazypay.transactionFlow.TransactionData.*;

public class AutoDebitV1MITests extends UserPlatformSupportData {

    public AutoDebitV1MITests() throws Exception {
        super();
    }

    @Description("To Verify the Autodebit V1 transaction with valid details for source Motor Insurance")
    @Feature("AutoDebitV1MI")
    @Test(priority = 1, groups = {"pay", "transaction", "regression", "sanity"})
    public void autoDebitV1MIValidRequest() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_MOTORINSURANCE);
        request.put("accessKey", ACCESS_KEY);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertNotNull(MakeTransaction.autoDebitPayMIPojo.transactionId, "verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.merchantTxnId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "SUCCESS");
    }

    @Description("To Verify the Autodebit V1 transaction API failed  for Merchant Aggregator with valid details for source Motor Insurance")
    @Feature("AutoDebitV1MI")
    @Test(priority = 2, groups = {"pay", "transaction", "regression", "sanity"})
    public void autoDebitV1MIValidRequestMAgg() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_MOTORINSURANCE);
        request.put("accessKey", ACCESS_KEY_2FA_DEVICERULE_MAgg);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "403","Verify that merchant aggregator flag enabled");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Forbidden","Verify that merchant aggregator flag enabled");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! You do not seem to have access","Verify that merchant aggregator flag enabled");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_ACCESS_DENIED");
    }


    @Description("To Verify the Autodebit V1 transaction API with Invalid source")
    @Feature("AutoDebitV1MI")
    @Test(priority = 3, groups = {"pay", "transaction", "regression"})
    public  void autoDebitV1MIWithInValidSource() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_CITRUS_SDK);
        request.put("accessKey", ACCESS_KEY);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertNotNull(MakeTransaction.autoDebitPayMIPojo.transactionId, "verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.merchantTxnId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "SUCCESS");
    }

    @Description("To Verify the Autodebit V1 transaction failed for new user who has never been on Lazypay")
    @Feature("AutoDebitV1MI")
    @Test(priority = 4, groups = {"pay", "transaction", "regression", "sanity"})
    public void autoDebitV1MIWithNewUserOnLP() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID41_NEWUSER);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_MOTORINSURANCE);
        request.put("accessKey", ACCESS_KEY);
        TransactionData.deleteUserAccount(request);
        TransactionData.deleteUserFromUsersTable(request);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "401","Verify the user is new");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Unauthorized","Verify the user is new");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "USER_NOT_FOUND");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Invalid Currency")
    @Feature("AutoDebitV1MI")
    @Test(priority = 5, groups = {"pay", "transaction", "regression"})
    public static void autoDebitV1MIWithInvalidCurrency() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_MOTORINSURANCE);
        request.put("accessKey", ACCESS_KEY);
        request.put("currency",INVALIDCURRENCY);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_CURRENCY", "Verify the Currency Format");
    }

    @Description("To Verify the MI Autodebit V1 transaction with invalid mobile")
    @Feature("AutoDebitV1MI")
    @Test(priority = 6, groups = {"pay", "transaction", "regression"})
    public void autoDebitV1MIWithInvalidMobile() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_INVALID);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_MOTORINSURANCE);
        request.put("accessKey", ACCESS_KEY);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "401");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Unauthorized","Mobile number should not be valid");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "USER_NOT_FOUND");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Missing mobile")
    @Feature("AutoDebitV1MI")
    @Test(priority = 7, groups = {"pay", "transaction", "regression"})
    public static void autoDebitV1MIWithMissingMobile() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("source", SOURCE_MOTORINSURANCE);
        request.put("accessKey", ACCESS_KEY);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "userDetails.mobile must not be blank", "Mobile number is not missing");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_REQ_PARAMETERS", "Any of the mandatory data is not missing");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Missing Source")
    @Feature("AutoDebitV1MI")
    @Test(priority = 8, groups = {"pay", "transaction", "regression"})
    public static void autoDebitV1MIWithMissingSource() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "source must not be blank", "Source is not blank");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Amount=0")
    @Feature("AutoDebitV1MI")
    @Test(priority = 9, groups = {"pay", "transaction", "regression"})
    public static void autoDebitV1MIWithZeroAmount() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT_ZERO);
        request.put("accessKey", ACCESS_KEY);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_AMOUNT","Amount is not equal to Zero");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Amount having four digit after decimal")
    @Feature("AutoDebitV1MI")
    @Test(priority = 10, groups = {"pay", "transaction", "regression"})
    public static void autoDebitV1MIWithAmountWithFourDecimal() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT_FOURDIGIT_AFTERDECIMAL);
        request.put("accessKey", ACCESS_KEY);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_AMOUNT", "Amount does not have four digit after decimal");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Invalid Amount Format")
    @Feature("AutoDebitV1MI")
    @Test(priority = 11, groups = {"pay", "transaction", "regression"})
    public static void autoDebitV1MIWithInvalidAmountFormat() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT_INVALID);
        request.put("accessKey", ACCESS_KEY);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_AMOUNT", "Amount format is not invalid");
    }

    @Description("To Verify the MI Autodebit V1 transaction with Invalid Merchant AccesKey")
    @Feature("AutoDebitV1MI")
    @Test(priority = 12, groups = {"pay", "transaction", "regression", "sanity"})
    public static void autoDebitV1MIWithInvalidAccessKey() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_INVALID);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "401");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Unauthorized");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_INVALID_ACCESS_KEY","AccessKey should be invalid");
    }

    @Description("To Verify the MI Autodebit V1 transaction with For User is not MITC and KYC signed")
    @Feature("AutoDebitV1MI")
    @Test(priority = 13, groups = {"pay", "transaction", "regression", "sanity"})
    public static void autoDebitV1MIWithNONMITCANDKYCUSER() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_NONMITCANDKYC);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! User is not mitc'ed.Complete Mitc to be eligible", "Verify that user has signed KYC and MITC");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_EXISTING_NON_MITCED_USER","Verify that user has signed KYC and MITC" );
    }

    @Description("To Verify the MI Autodebit V1 transaction for Non Autodebit Merchant")
    @Feature("AutoDebitV1MI")
    @Test(priority = 14, groups = {"pay", "transaction", "regression", "sanity"})
    public static void autoDebitV1MIWithNONAutoDebitMerchant() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_VALID45);
        request.put("amount", TRANSACTION_AMOUNT);
        request.put("accessKey", ACCESS_KEY_AUTODEBIT_FALSE);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "401");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Unauthorized");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! You do not seem to have access", "Verify that merchant enabled for autodebit");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_ACCESS_DENIED","Verify that merchant enabled for autodebit" );
    }

    @Description("To Verify the MI Autodebit V1 transaction for more than available credit limit")
    @Feature("AutoDebitV1MI")
    @Test(priority = 15, groups = {"pay", "transaction", "regression", "sanity"})
    public static void autoDebitV1MIWithMoreThanAvailableCreditLimit() throws Exception {
        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("amount", TRANSACTION_AMOUNT_HIGH);
        request.put("accessKey", ACCESS_KEY);
        request.put("source", SOURCE_MOTORINSURANCE);
        MakeTransaction.makeAutoDebitMITransactionHash(request);
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.status, "400");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.message, "Oops!! The transaction value seems to be higher than your available LazyPay credit limit", "Verify the txn amount is not higher than available credit limit");
        Assert.assertEquals(MakeTransaction.autoDebitPayMIPojo.errorCode, "LP_EXCEEDS_USER_MAX_TXN_LIMIT","Verify the txn amount is not higher than available credit limit");
    }
}
