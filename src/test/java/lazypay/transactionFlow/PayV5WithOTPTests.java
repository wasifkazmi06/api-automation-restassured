package lazypay.transactionFlow;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PayV5WithOTPTests {

    static Map<String, Object> mapOTP;

    @Description("To verify pay V5 for a valid user with source as CITRUS_SDK")
    @Feature("PayWithOTPV5")
    @Test(priority = 1, groups = {"pay", "transaction", "regression", "sanity"})
    public static void payV5V5LPSourceCitrusSDK() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        mapOTP = (Map<String, Object>) MakeTransaction.payPojo.responseData.get("OTP");

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId,"verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.payPojo.amount, TransactionData.TRANSACTION_AMOUNT);
        Assert.assertEquals(mapOTP.get("reason").toString() , "Transaction is successful");
        Assert.assertEquals(mapOTP.get("status").toString() , "SUCCESS");
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("access_token"));
        Assert.assertEquals(MakeTransaction.payPojo.token.get("token_type"), "bearer");
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("refresh_token"));
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("expires_in"));
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile"), TransactionData.USER_MOBILE_VALID3);
    }

    @Description("To data types of pay V5 response when source is CITRUS_SDK")
    @Feature("PayWithOTPV5")
    @Test(priority = 2, groups = {"pay", "transaction", "regression", "sanity"})
    public void payV5DataTypeSourceCitrusSDK() {

        Assert.assertEquals(MakeTransaction.payPojo.transactionId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.amount.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.currency.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.signature.getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.responseData.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("reason").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("pgResponseCode").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("status").getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.token.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("access_token").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("token_type").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("refresh_token").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("expires_in").getClass().getSimpleName(), Integer.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.productSkuDetails.getClass().getSimpleName(), ArrayList.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.userDetails.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("firstName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("firstName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        if(MakeTransaction.payPojo.userDetails.get("lastName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("lastName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile").getClass().getSimpleName(), String.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("email") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("email").getClass().getSimpleName(), String.class.getSimpleName());
        }
    }

    @Description("To verify pay V5 for a valid user with source as CITRUS_SSLV2")
    @Feature("PayWithOTPV5")
    @Test(priority = 3, groups = {"pay", "transaction", "regression", "sanity"})
    public static void payV5V5SourceCitrusSSLV5() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        mapOTP = (Map<String, Object>) MakeTransaction.payPojo.responseData.get("OTP");

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId,"verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.payPojo.amount, TransactionData.TRANSACTION_AMOUNT);
        Assert.assertEquals(mapOTP.get("reason").toString() , "Transaction is successful");
        Assert.assertEquals(mapOTP.get("status").toString() , "SUCCESS");
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile"), TransactionData.USER_MOBILE_VALID3);
    }

    @Description("To data types of pay V5 response when source is CITRUS_SSLV2")
    @Feature("PayWithOTPV5")
    @Test(priority = 4, groups = {"pay", "transaction", "regression", "sanity"})
    public void payV5DataTypeSourceCitrusSSLV5() {

        Assert.assertEquals(MakeTransaction.payPojo.transactionId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.amount.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.currency.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.signature.getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.responseData.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("reason").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("pgResponseCode").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("status").getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.productSkuDetails.getClass().getSimpleName(), ArrayList.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.userDetails.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("firstName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("firstName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        if(MakeTransaction.payPojo.userDetails.get("lastName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("lastName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile").getClass().getSimpleName(), String.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("email") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("email").getClass().getSimpleName(), String.class.getSimpleName());
        }
    }

    @Description("To verify pay V5 for a valid user with source as STANDALONE_API")
    @Feature("PayWithOTPV5")
    @Test(priority = 5, groups = {"pay", "transaction", "regression", "sanity"})
    public static void payV5V5SourceStandaloneAPI() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        mapOTP = (Map<String, Object>) MakeTransaction.payPojo.responseData.get("OTP");

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId,"verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.payPojo.amount, TransactionData.TRANSACTION_AMOUNT);
        Assert.assertEquals(mapOTP.get("reason").toString() , "Transaction is successful");
        Assert.assertEquals(mapOTP.get("status").toString() , "SUCCESS");
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("access_token"));
        Assert.assertEquals(MakeTransaction.payPojo.token.get("token_type"), "bearer");
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("refresh_token"));
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("expires_in"));
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile"), TransactionData.USER_MOBILE_VALID3);
    }

    @Description("To data types of pay V5 response when source is STANDALONE_API")
    @Feature("PayWithOTPV5")
    @Test(priority = 6, groups = {"pay", "transaction", "regression", "sanity"})
    public void payV5DataTypeSourceStandaloneAPI() {

        Assert.assertEquals(MakeTransaction.payPojo.transactionId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.amount.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.currency.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.signature.getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.responseData.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("reason").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("pgResponseCode").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("status").getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.token.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("access_token").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("token_type").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("refresh_token").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("expires_in").getClass().getSimpleName(), Integer.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.productSkuDetails.getClass().getSimpleName(), ArrayList.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.userDetails.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("firstName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("firstName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        if(MakeTransaction.payPojo.userDetails.get("lastName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("lastName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile").getClass().getSimpleName(), String.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("email") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("email").getClass().getSimpleName(), String.class.getSimpleName());
        }
    }

    @Description("To verify pay V5 for a valid user with source as STANDALONE_REDIRECT")
    @Feature("PayWithOTPV5")
    @Test(priority = 7, groups = {"pay", "transaction", "regression", "sanity"})
    public static void payV5V5SourceStandaloneRedirect() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        mapOTP = (Map<String, Object>) MakeTransaction.payPojo.responseData.get("OTP");

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId,"verify that user is valid and transaction data should be correct");
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId, MakeTransaction.MTX);
        Assert.assertEquals(MakeTransaction.payPojo.amount, TransactionData.TRANSACTION_AMOUNT);
        Assert.assertEquals(mapOTP.get("reason").toString() , "Transaction is successful");
        Assert.assertEquals(mapOTP.get("status").toString() , "SUCCESS");
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("access_token"));
        Assert.assertEquals(MakeTransaction.payPojo.token.get("token_type"), "bearer");
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("refresh_token"));
        Assert.assertNotNull(MakeTransaction.payPojo.token.get("expires_in"));
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile"), TransactionData.USER_MOBILE_VALID3);
    }

    @Description("To data types of pay V5 response when source is STANDALONE_REDIRECT")
    @Feature("PayWithOTPV5")
    @Test(priority = 8, groups = {"pay", "transaction", "regression", "sanity"})
    public void payV5DataTypeSourceStandaloneRedirect() {

        Assert.assertEquals(MakeTransaction.payPojo.transactionId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.merchantOrderId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.amount.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.currency.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.signature.getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.responseData.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("reason").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("pgResponseCode").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(mapOTP.get("status").getClass().getSimpleName(), String.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.token.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("access_token").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("token_type").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("refresh_token").getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.token.get("expires_in").getClass().getSimpleName(), Integer.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(MakeTransaction.payPojo.productSkuDetails.getClass().getSimpleName(), ArrayList.class.getSimpleName());

        Assert.assertEquals(MakeTransaction.payPojo.userDetails.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("firstName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("firstName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        if(MakeTransaction.payPojo.userDetails.get("lastName") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("lastName").getClass().getSimpleName(), String.class.getSimpleName());
        }
        Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("mobile").getClass().getSimpleName(), String.class.getSimpleName());
        if(MakeTransaction.payPojo.userDetails.get("email") != null){
            Assert.assertEquals(MakeTransaction.payPojo.userDetails.get("email").getClass().getSimpleName(), String.class.getSimpleName());
        }
    }

    @Description("To verify pay v5 for a valid user with blank txn ref no.")
    @Feature("PayWithOTPV5")
    @Test(priority = 9, groups = {"pay", "transaction", "regression","agg"})
    public void PayV5V5BlankTxnLP() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);
        request.put("txnRefNo", " ");

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! Something went wrong","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_TXN_REF_NO_REQUIRED");
    }

    @Description("To verify pay v5 for a valid user with invalid txn ref no.")
    @Feature("PayWithOTPV5")
    @Test(priority = 10, groups = {"pay", "transaction", "regression","agg"})
    public void PayV5V5InvalidTxnLP() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);
        request.put("txnRefNo", "test");

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! Transaction timed out. Please place your order again","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_TXN_TIMED_OUT");
    }

    @Description("To verify pay v5 for a valid user with balnk OTP")
    @Feature("PayWithOTPV5")
    @Test(priority = 11, groups = {"pay", "transaction", "regression","agg"})
    public void PayV5V5BlankOtpLP() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);
        request.put("otp", " ");

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! OTP field seems to be missing","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_OTP_REQUIRED");
    }

    @Description("To verify pay v5 for a valid user with invalid OTP")
    @Feature("PayWithOTPV5")
    @Test(priority = 12, groups = {"pay", "transaction", "regression","agg"})
    public void PayV5V5InvalidOtpLP() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);
        request.put("otp", "0000");

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        Assert.assertEquals(MakeTransaction.payPojo.status, "401","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Unauthorized","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Sorry! The OTP entered is incorrect. Last 1 chance to enter correct OTP before account is locked","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INCORRECT_OTP");
    }

    @Description("To verify pay v5 for a valid user with 3 incorrect OTP")
    @Feature("PayWithOTPV5")
    @Test(priority = 13, groups = {"pay", "transaction", "regression","agg"})
    public void PayV5V5With3IncorrectOtpLP() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);
        request.put("otp", "0000");

        MakeTransaction.makeLPOTPTransactionHash(request, 2);

        Assert.assertEquals(MakeTransaction.payPojo.status, "401","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Unauthorized","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! Your LazyPay account seems to be locked3 incorrect OTP attempts. " +
                "Contact our customer service to unlock","verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_ACCOUNT_LOCKED");

        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID1, "null");
    }

    @Description("To verify pay v4 for a valid user with custom params")
    @Feature("PayWithOTPV5")
    @Test(priority = 14, groups = {"pay", "transaction", "regression","agg"})
    public void PayV5V5SignatureRequiredLP() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID3);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchantId", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("initiateVersion", TransactionData.INITIATE_PAY_V5);
        request.put("payVersion", TransactionData.PAY_V5);
        request.put("signature", " ");

        MakeTransaction.makeLPOTPTransactionHash(request, 1);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400", "verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request", "verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! Something went wrong", "verify that txn Id should not be given in request");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_SIGNATURE_REQUIRED");
    }
}