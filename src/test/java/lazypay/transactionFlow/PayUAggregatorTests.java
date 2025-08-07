package lazypay.transactionFlow;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.AccountLink;
import lazypay.MakeTransaction;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.getTransactionHistory.Transaction;

public class PayUAggregatorTests {


    static String oldToken;
    static String newToken;

    @Description("To verify that an eligible user can transact on an aggregator merchant and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 1, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser1Token1Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_1_AGG_USER_13);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }


    @Description("To verify that another eligible user can transact on the same aggregator merchant and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 2, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser2Token2Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_2_AGG_USER_14);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 3, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token3Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_3_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 4, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token4Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_4_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 5, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token5Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_5_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 6, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token6Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_6_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 7, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token1Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_2, TransactionData.AUTH_TOKEN_1_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 8, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token2Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_2, TransactionData.AUTH_TOKEN_2_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that an eligible user can transact on an aggregator merchant and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 9, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser1Token3Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_2, TransactionData.AUTH_TOKEN_3_AGG_USER_13);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that another eligible user can transact on an aggregator merchant and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 10, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser2Token4Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_2, TransactionData.AUTH_TOKEN_4_AGG_USER_14);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 11, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token5Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_2, TransactionData.AUTH_TOKEN_5_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 12, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token6Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_2, TransactionData.AUTH_TOKEN_6_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 13, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token1Merchant2SubMerchant3() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_3, TransactionData.AUTH_TOKEN_1_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 14, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token2Merchant2SubMerchant3() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_3, TransactionData.AUTH_TOKEN_2_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 15, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token3Merchant2SubMerchant3() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_3, TransactionData.AUTH_TOKEN_3_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that token generated on an aggregator merchant and it's sub-merchant cannot be used on another sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 16, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token4Merchant2SubMerchant3() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_3, TransactionData.AUTH_TOKEN_4_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that an eligible user can transact on an aggregator merchant and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 17, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser1Token5Merchant2SubMerchant3() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_3, TransactionData.AUTH_TOKEN_5_AGG_USER_13);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }


    @Description("To verify that another eligible user can transact on the same aggregator merchant and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 18, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser2Token6Merchant2SubMerchant3() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_3, TransactionData.AUTH_TOKEN_6_AGG_USER_14);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that an eligible user can transact on a direct merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 19, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPDirectUser1Token9MerchantDirect() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V0,
                TransactionData.PAY_V0, TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,
                null, TransactionData.AUTH_TOKEN_9_DIRECT_USER_13);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that an eligible user cannot initiate a transaction on an aggregator merchant using the direct flow.")
    @Feature("AggregatorFlow")
    @Test(priority = 20, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPUser2Token2DirectFlowAggMerchant() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V0,
                TransactionData.PAY_V0, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                null, TransactionData.AUTH_TOKEN_2_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_CLIENT_ID");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
    }

    @Description("To verify that an eligible user can link an their account to a direct merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 21, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPUser1AccountLinkDirectMerchant() throws Exception {

        AccountLink.accountLinkMethod(TransactionData.USER_MOBILE_VALID14, "", TransactionData.ACCESS_KEY);
        Assert.assertEquals(AccountLink.sendOTPResponse.statusCode(), 200);
        Assert.assertNotNull(AccountLink.validateOTPPojo.access_token);
    }

    @Description("To verify that an eligible user cannot link an their account to an aggregator merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 22, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPUser2AccountLinkAggMerchant() throws Exception {

        AccountLink.accountLinkMethod(TransactionData.USER_MOBILE_VALID13, "", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        Assert.assertEquals(AccountLink.sendOTPResponse.statusCode(), 403);
        Assert.assertEquals(AccountLink.validateOTPPojo.status, "403");
        Assert.assertEquals(AccountLink.validateOTPPojo.errorCode, "LP_ACCESS_DENIED");
        Assert.assertEquals(AccountLink.validateOTPPojo.message, "Oops!! You do not seem to have access");
    }

    @Description("To verify that an eligible user can transact on an aggregator merchant which is allowed merchant level auto-debit token and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 23, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser1Token7MerchantAllowMerchantLevelAutoDebitSubMerchant4() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_ALLOW_MERCHANT_LEVEL_AUTODEBIT,
                TransactionData.TRANSACTION_AMOUNT, TransactionData.SUB_MERCHANT_ID_4,
                TransactionData.AUTH_TOKEN_7_AGG_USER_13);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }


    @Description("To verify that another eligible user can transact on an aggregator merchant which is allowed merchant level auto-debit token and it's sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 24, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser2Token8MerchantAllowMerchantLevelAutoDebitSubMerchant4() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_ALLOW_MERCHANT_LEVEL_AUTODEBIT,
                TransactionData.TRANSACTION_AMOUNT, TransactionData.SUB_MERCHANT_ID_4, TransactionData.AUTH_TOKEN_8_AGG_USER_14);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that an eligible user can transact on an aggregator merchant which is allowed merchant level auto-debit token using the same token on a different sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 25, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser1Token7MerchantAllowMerchantLevelAutoDebitSubMerchant5() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_ALLOW_MERCHANT_LEVEL_AUTODEBIT,
                TransactionData.TRANSACTION_AMOUNT, TransactionData.SUB_MERCHANT_ID_5,
                TransactionData.AUTH_TOKEN_7_AGG_USER_13);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }


    @Description("To verify that another eligible user can transact on an aggregator merchant which is allowed merchant level auto-debit token using the same token on a different sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 26, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggUser2Token8MerchantAllowMerchantLevelAutoDebitSubMerchant5() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_ALLOW_MERCHANT_LEVEL_AUTODEBIT,
                TransactionData.TRANSACTION_AMOUNT, TransactionData.SUB_MERCHANT_ID_5,
                TransactionData.AUTH_TOKEN_8_AGG_USER_14);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that an eligible user cannot transact using an allowMerchantLevelAutoDebit token on a different merchant/sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 27, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser1Token7Merchant1SubMerchant1() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_7_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.status, "401");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Unauthorized");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! You do not seem to have access");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify that an eligible user cannot transact using an allowMerchantLevelAutoDebit token on a different merchant/sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 28, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggDifferentSubMerchantUser2Token8Merchant1SubMerchant2() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, TransactionData.AUTH_TOKEN_8_AGG_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.status, "401");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Unauthorized");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! You do not seem to have access");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify that an eligible user cannot transact on a direct merchant using the aggregator flow.")
    @Feature("AggregatorFlow")
    @Test(priority = 29, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPDirectMerchantAggFlowNotAllowed() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID13, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID, TransactionData.AUTH_TOKEN_1_AGG_USER_13);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_INVALID_REQ_PARAMETERS");
    }

    @Description("To verify that an eligible user cannot transact on a disabled sub-merchant.")
    @Feature("AggregatorFlow")
    @Test(priority = 30, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggFlowDisabledSubMerchantNotAllowed() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID14, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_DISABLED, TransactionData.AUTH_TOKEN_10_DIRECT_USER_14);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Sub-merchant is disabled on the LP system");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_SUB_MERCHANT_DISABLED");
    }

    @Description("To verify that an eligible user can transact on an aggregator merchant and it's sub-merchant using the latest token generated.")
    @Feature("AggregatorFlow")
    @Test(priority = 31, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggNewTokenAllowed() throws Exception {

        MakeTransaction.makeLPOTPTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, false);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);

        oldToken = "Bearer " + MakeTransaction.payPojo.token.get("access_token");

        MakeTransaction.makeLPOTPTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, false);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);

        newToken = "Bearer " + MakeTransaction.payPojo.token.get("access_token");

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, newToken);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that an eligible user cannot transact on an aggregator merchant and it's sub-merchant using an older token.")
    @Feature("AggregatorFlow")
    @Test(priority = 32, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPAggOldTokenNotAllowed() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_1, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_1, oldToken);

        Assert.assertEquals(MakeTransaction.payPojo.status, "401");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Unauthorized");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! You do not seem to have access");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify that an eligible user can transact on a direct merchant using the latest token generated.")
    @Feature("AggregatorFlow")
    @Test(priority = 33, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPDirectNewTokenAllowed() throws Exception {

        MakeTransaction.makeLPOTPTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V0,
                TransactionData.PAY_V0, TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT,"", false);

        oldToken = "Bearer " + MakeTransaction.payPojo.token.get("access_token");

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);

        MakeTransaction.makeLPOTPTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V0,
                TransactionData.PAY_V0, TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT, "", false);

        newToken = "Bearer " + MakeTransaction.payPojo.token.get("access_token");

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V0,
                TransactionData.PAY_V0, TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT, "", newToken);

        Assert.assertNotNull(MakeTransaction.payPojo.transactionId);
        Assert.assertNotNull(MakeTransaction.payPojo.amount);
    }

    @Description("To verify that an eligible user cannot transact on a direct merchant using an older token.")
    @Feature("AggregatorFlow")
    @Test(priority = 34, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPDirectOldTokenNotAllowed() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V0,
                TransactionData.PAY_V0, TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT, "", oldToken);

        Assert.assertEquals(MakeTransaction.payPojo.status, "401");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Unauthorized");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Oops!! You do not seem to have access");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_ACCESS_DENIED");
    }

    @Description("To verify that an eligible user cannot make an auto-debit transaction on an sub-merchant disabled on auto-debit flow.")
    @Feature("AggregatorFlow")
    @Test(priority = 35, groups = {"agg", "regression", "sanity"})
    public void PayWithTokenLPSubMerchantAutoDebitDisabledNotAllowed() throws Exception {

        MakeTransaction.makeLPAutoDebitTransaction(TransactionData.USER_MOBILE_VALID15, "", TransactionData.INITIATE_PAY_V5,
                TransactionData.PAY_V5, TransactionData.ACCESS_KEY_AGGREGATOR_2, TransactionData.TRANSACTION_AMOUNT,
                TransactionData.SUB_MERCHANT_ID_AUTO_DEBIT_DISABLED, TransactionData.AUTH_TOKEN_DISABLED_SUB_MERCHANT_USER_15);

        Assert.assertEquals(MakeTransaction.payPojo.status, "400");
        Assert.assertEquals(MakeTransaction.payPojo.error, "Bad Request");
        Assert.assertEquals(MakeTransaction.payPojo.message, "Sub-merchant is disabled on the LP system");
        Assert.assertEquals(MakeTransaction.payPojo.errorCode, "LP_SUB_MERCHANT_DISABLED");
    }
}
