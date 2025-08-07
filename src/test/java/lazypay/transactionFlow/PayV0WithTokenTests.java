package lazypay.transactionFlow;

import api.lazypay.transaction.PayV0;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.LazypayConstants;
import lazypay.MakeTransaction;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.PayPojo;
import util.ReturnRandomTxnId;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class PayV0WithTokenTests {

    public PayV0WithTokenTests() throws Exception {
    }

    public static String signature;
    public static String authToken;
    static public String MTX;
    static public String authToken_EmailRequired;
    static public String authToken_USerMobileMaxedOut;
    static public String authToken_MoreThenLowCredit;

    GenerateToken generateToken = new GenerateToken();

    PayV0 payV0 = new PayV0();

    @Description("To verify pay v0 for a valid user")
    @Feature("PayWithTokenV0")
    @Test(priority = 1, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP() throws Exception {

        /*HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("payVersion", TransactionData.PAY_V0);

        MakeTransaction.makeLPAutoDebitTransactionHash(request);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId, "verify that user is valid and transaction data is correct");
        Assert.assertNotNull(MakeTransaction.payPojo.amount);*/

        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        authToken = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY, TransactionData.USER_MOBILE_VALID1, "","","");
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", "")
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertNotNull(payPojo.transactionId, "verify that user is valid and transaction data is correct");
        Assert.assertNotNull(payPojo.amount);
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);
    }

    @Description("To verify pay with token v0 for a valid user having invalid token")
    @Feature("PayWithTokenV0")
    @Test(priority = 2, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_InvalidTokenLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken+ "test");
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "401","verify that incorrect token should be passed");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);
    }

    @Description("To verify pay with token v0 for a valid user having expired token")
    @Feature("PayWithTokenV0")
    @Test(priority = 3, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP_ExpiredTokenLP() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", TransactionData.AUTH_TOKEN);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "401","verify that incorrect token should be passed");
        Assert.assertEquals(payPojo.errorCode, "LP_ACCESS_DENIED");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with duplicate txn")
    @Feature("PayWithTokenV0")
    @Test(priority = 4, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_DuplicateTxnLP() throws Exception {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", TransactionData.MTX_DUPLICATE_ID)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "401","verify that transaction id is not unique");
        Assert.assertEquals(payPojo.errorCode, "LP_SIGNATURE_MISMATCH", "verify that transaction should be duplicate");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay v0 for a valid user with blank mobile number")
    @Feature("PayWithTokenV0")
    @Test(priority = 5, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_BlankMobile() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", "")
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_DETAILS_REQUIRED", "verify that mobile should not be valid");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay v0 for a valid user with invalid mobile")
    @Feature("PayWithTokenV0")
    @Test(priority = 6, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_InvalidMobile() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", "123456789")
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400");
        Assert.assertEquals(payPojo.errorCode, "LP_INVALID_MOBILE", "verify that mobile should not be valid");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay v0 for a valid user without mail")
    @Feature("PayWithTokenV0")
    @Test(priority = 7, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP_WithoutMail() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", "")
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertNotNull(payPojo.transactionId, "verify that transaction data should be correct");
        Assert.assertNotNull(payPojo.amount);
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay v0 for a valid user without first name")
    @Feature("PayWithTokenV0")
    @Test(priority = 8, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_WithoutFirstName() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        authToken_EmailRequired = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY_EMAIL_REQUIRED, TransactionData.USER_MOBILE_VALID10, TransactionData.USER_EMAIL,TransactionData.FIRST_NAME,TransactionData.LAST_NAME);
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY_EMAIL_REQUIRED+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY_EMAIL_REQUIRED);
        headerDetails.put("Authorization", authToken_EmailRequired);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", TransactionData.LAST_NAME)
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400", "verify that first name is not given but last name");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_DETAILS_REQUIRED", "verify that last name should not be given");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);
    }

    @Description("To verify pay v0 for a valid user without first name")
    @Feature("PayWithTokenV0")
    @Test(priority = 9, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_WithoutLastName() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY_EMAIL_REQUIRED+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY_EMAIL_REQUIRED);
        headerDetails.put("Authorization", authToken_EmailRequired);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", TransactionData.FIRST_NAME)
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400", "verify that last name is not given but first name");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_DETAILS_REQUIRED", "verify that last name should not be given");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);
    }

    @Description("To verify pay with token v0 for a blocked user")
    @Feature("PayWithTokenV0")
    @Test(priority = 10, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP_BlockedUserLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


//        authToken = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY, TransactionData.USER_MOBILE_VALID1, TransactionData.USER_EMAIL,"","");
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID1, "blocked");

        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);
        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID1, "null");

        Assert.assertEquals(payPojo.status, "400", "verify that user's status should be blocked");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_BLOCKED");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a locked user")
    @Feature("PayWithTokenV0")
    @Test(priority = 11, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_LockedUserLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID10, "locked");

        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);
        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID10, "null");


        Assert.assertEquals(payPojo.status, "400", "verify that user's status should be locked");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_LOCKED");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a fraud user")
    @Feature("PayWithTokenV0")
    @Test(priority = 12, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_BlackListedUserLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

//        authToken = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY, TransactionData.USER_MOBILE_VALID10, "","","");
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);

        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID10, "blacklisted");

        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL_FRAUD)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);
        TransactionData.updateRestriction(TransactionData.USER_MOBILE_VALID10, "null");

        Assert.assertEquals(payPojo.status, "400","verify that user should be blacklisted");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_BLACKLISTED");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }


    @Description("To verify pay with token v0 for a opted out user")
    @Feature("PayWithTokenV0")
    @Test(priority = 13, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_OptedOutUserLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
//        DeleteUser.deleteUserByMobile(TransactionData.USER_MOBILE_VALID10, "lp");

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        TransactionData.updateOptedOutUser(TransactionData.USER_MOBILE_VALID10,"1", "OPTED_OUT" );

        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);
        TransactionData.updateOptedOutUser(TransactionData.USER_MOBILE_VALID10,"0", "ACTIVE" );

        Assert.assertEquals(payPojo.status, "400", "verify that user's status should be opted out");
        Assert.assertEquals(payPojo.errorCode, "LP_USER_OPTED_OUT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with invalid amount")
    @Feature("PayWithTokenV0")
    @Test(priority = 14, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_InvalidAmount() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT_INVALID)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400","verify that entered amount should be invalid");
        Assert.assertEquals(payPojo.errorCode, "LP_INVALID_AMOUNT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with invalid currency")
    @Feature("PayWithTokenV0")
    @Test(priority = 15, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_InvalidCurrencyLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", "IN")
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400","currency given should be invalid");
        Assert.assertEquals(payPojo.errorCode, "LP_INVALID_CURRENCY");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }


    @Description("To verify pay with token v0 for a valid user with without signature")
    @Feature("PayWithTokenV0")
    @Test(priority = 16, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_WithoutSignatureLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("Authorization", authToken);
//        headerDetails.put("signature", "");


        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "401","verify that signature is not given in request");
        Assert.assertEquals(payPojo.errorCode, "LP_SIGNATURE_MISMATCH");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with incorrect signature")
    @Feature("PayWithTokenV0")
    @Test(priority = 17, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_IncorrectSignatureLP() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature + "test");

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "401", "verify that incorrect signature is given");
        Assert.assertEquals(payPojo.errorCode, "LP_SIGNATURE_MISMATCH");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with more than max txn limit")
    @Feature("PayWithTokenV0")
    @Test(priority = 18, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP_MaxTxnLimit() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400", "verify that txn amount should be more than 9999");
        Assert.assertEquals(payPojo.message, TransactionData.MAX_TXN_AMOUNT_ERROR_MSG_PAY);
        Assert.assertEquals(payPojo.errorCode, "LP_PER_TXN_MAX_LIMIT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with txn amount more than credit limit")
    @Feature("PayWithTokenV0")
    @Test(priority = 19, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_MoreThanCreditLimit() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        authToken_MoreThenLowCredit = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY, TransactionData.USER_MOBILE_LOW_CREDIT_LIMIT, TransactionData.USER_EMAIL,"","");

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT_HIGH, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken_MoreThenLowCredit);
        headerDetails.put("signature", signature);


        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT_HIGH)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_LOW_CREDIT_LIMIT)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400","verify that txn amount should be more than credit limit");
        Assert.assertEquals(payPojo.errorCode, "LP_EXCEEDS_USER_MAX_TXN_LIMIT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with txn amount more than available credit limit")
    @Feature("PayWithTokenV0")
    @Test(priority = 20, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP_moreThanAvailableCreditLimit() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        authToken_USerMobileMaxedOut = "Bearer "+ generateToken.generateToken(TransactionData.ACCESS_KEY, TransactionData.USER_MOBILE_MAXED_OUT, TransactionData.USER_EMAIL,"","");
        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+ (EligibilityV0Tests.availableLimitMaxedOutUser + 1), TransactionData.ACCESS_KEY);

        headerDetails.put("Authorization", authToken_USerMobileMaxedOut);
        headerDetails.put("signature", signature);



        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", String.valueOf(EligibilityV0Tests.availableLimitMaxedOutUser + 1))
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_MAXED_OUT)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertEquals(payPojo.status, "400","verify that txn amount should be more than available credit limit");
        Assert.assertEquals(payPojo.errorCode, "LP_EXCEEDS_USER_MAX_TXN_LIMIT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user without optional fields")
    @Feature("PayWithTokenV0")
    @Test(priority = 21, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_withoutOptionalFields() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        JSONObject payRequestJson = new JSONObject(PayRequest);
        payRequestJson.remove("customParams");
        payRequestJson.remove("address");
        payRequestJson.remove("productSkuDetails");

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, payRequestJson.toString());

        Assert.assertNotNull(payPojo.transactionId, "verify if txn data in request body is correct");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);
    }



    @Description("To verify pay with token v0 for a valid user without custom params")
    @Feature("PayWithTokenV0")
    @Test(priority = 22, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_withoutCustomParams() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        JSONObject payRequestJson = new JSONObject(PayRequest);
//        payRequestJson.put("customParams", " ");
        payRequestJson.remove("customParams");
        String s = payRequestJson.toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, payRequestJson.toString());

        Assert.assertNotNull(payPojo.transactionId, "verify if custom params are given and txn data in request is correct");
        Assert.assertNotNull(payPojo.amount);
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user with more than 30 char custom param key")
    @Feature("PayWithTokenV0")
    @Test(priority = 23, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_customParamKeyLimit() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        JSONObject payRequestJson = new JSONObject(PayRequest);
        payRequestJson.getJSONObject("customParams").put("custom_having_character_more_than_30", "prop1");

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, payRequestJson.toString());

        Assert.assertEquals(payPojo.status, "400","verify that custom param key should consist more than 30 chars");
        Assert.assertEquals(payPojo.errorCode, "CUSTOM_PARAMS_KEY_LENGTH_EXCEEDS_LIMIT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);

    }

    @Description("To verify pay with token v0 for a valid user")
    @Feature("PayWithTokenV0")
    @Test(priority = 24, groups = {"pay", "transaction", "regression"})
    public void PayWithTokenV0LP_moreThan10CustomParams() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + TransactionData.ACCESS_KEY + "&transactionId=" + MTX
                + "&amount=" + TransactionData.TRANSACTION_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID10)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        JSONObject payRequestJson = new JSONObject(PayRequest);
        for (int i = 0; i <= 10; i++) {
            payRequestJson.getJSONObject("customParams").put("custom_prop" + i, "prop1");

        }

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, payRequestJson.toString());

        Assert.assertEquals(payPojo.status, "400", "verify that more than 10 custom params are given in request");
        Assert.assertEquals(payPojo.errorCode, "CUSTOM_PARAMS_EXCEEDS_LIMIT");
        Reporter.log("\nPay LP Txn Response:" + payPojo.transactionId, true);
    }

    @Description("To verify pay with token v0 for a valid user with less than max txn limit")
    @Feature("PayWithTokenV0")
    @Test(priority = 25, groups = {"pay", "transaction", "regression", "sanity"})
    public void PayWithTokenV0LP_LessThanMaxTxnLimit() throws Exception {
        MTX = ReturnRandomTxnId.returnTxnIDMethod("MTX");

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey="+TransactionData.ACCESS_KEY+"&transactionId="+MTX
                +"&amount="+TransactionData.TRANSACTION_MAX_AMOUNT, TransactionData.ACCESS_KEY);
        headerDetails.put("Authorization", authToken);
        headerDetails.put("signature", signature);

        String PayRequest = new StringTemplate(LazypayConstants.PAY_WITH_TOKEN)
                .replace("value", TransactionData.TRANSACTION_MAX_AMOUNT)
                .replace("currency", TransactionData.CURRENCY)
                .replace("mobile", TransactionData.USER_MOBILE_VALID1)
                .replace("email", TransactionData.USER_EMAIL)
                .replace("firstName", "")
                .replace("lastName", "")
                .replace("merchantTxnId", MTX)
                .toString();

        PayPojo payPojo = payV0.post(queryParamDetails, headerDetails, PayRequest);

        Assert.assertNotNull(payPojo.transactionId, "verify that user is valid and transaction data is correct");
        Assert.assertNotNull(payPojo.amount);
    }
}






