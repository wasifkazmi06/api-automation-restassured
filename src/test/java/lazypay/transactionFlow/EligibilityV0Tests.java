package lazypay.transactionFlow;

import api.vaultTM.BalanceCache;
import dbUtils.Lazypay_MySQL_DBAccessObject;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.CheckEligibility;
import lazypay.MakeRepayment;
import lazypay.MakeTransaction;
import mpl.TestData;
import org.testng.Assert;
import org.testng.annotations.*;
import pojos.vaultTM.BalanceCachePojo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EligibilityV0Tests {

    public static String signature;

    MakeRepayment makeRepayment = new MakeRepayment("", 0, "");

    public BalanceCache balanceCache = new BalanceCache();
    public BalanceCachePojo balanceCachePojo = new BalanceCachePojo();

    public String accountIdMaxedOutUser;
    public static double availableLimitMaxedOutUser = 1942;
    public static double transactionAmountMaxedOutUser = 8002;

    public EligibilityV0Tests() throws Exception {
    }

    @AfterClass
    public void finishUp() throws SQLException, ClassNotFoundException {
        Lazypay_MySQL_DBAccessObject.closeMySqlDbConnection();
    }

    @Description("To set up data requirement for sanity suite")
    @Feature("UserDataSetUP")
    @Test(priority = 1, dataProvider = "user-mobile", dataProviderClass = TransactionData.class, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void sanityDataSetUp(String mobile) throws Exception {

        makeRepayment.verifyRepayDetails(mobile);

        if(makeRepayment.repayDetailsPojo.dueAmount>1 || makeRepayment.repayDetailsPojo.totalOutstanding > 10000) {
            makeRepayment.makeRepaymentMethod(mobile);
        }

        if (mobile.equals(TransactionData.USER_MOBILE_MAXED_OUT)) {

            if (makeRepayment.repayDetailsPojo.totalOutstanding > transactionAmountMaxedOutUser) {
                Allure.addAttachment("UserDataSetUP", "User having outstanding of more than 8001");
            } else if (makeRepayment.repayDetailsPojo.totalOutstanding < transactionAmountMaxedOutUser
                    && (makeRepayment.repayDetailsPojo.totalOutstanding >= transactionAmountMaxedOutUser - 1)) {
                MakeTransaction.makeLPOTPTransaction(TransactionData.USER_MOBILE_MAXED_OUT, "", "v0", "v0",
                        TransactionData.ACCESS_KEY, TransactionData.TRANSACTION_AMOUNT, "", false);
            } else {
                String amount = String.valueOf(transactionAmountMaxedOutUser - makeRepayment.repayDetailsPojo.totalOutstanding);
                MakeTransaction.makeLPOTPTransaction(TransactionData.USER_MOBILE_MAXED_OUT, "", "v0", "v0",
                        TransactionData.ACCESS_KEY, amount, "", false);
            }

            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();

            accountIdMaxedOutUser = TransactionData.getAccountDetails(TransactionData.USER_MOBILE_MAXED_OUT, TestData.TENANT_LAZYPAY, "account_id");
            balanceCachePojo = balanceCache.get(queryParamDetails, headerDetails, accountIdMaxedOutUser);
            availableLimitMaxedOutUser = (balanceCachePojo.defaultCommitted.amount - balanceCachePojo.defaultPendingOutgoing.amount) * -1;
        }
        TransactionData.updateRestriction(mobile, "");
    }

    @Description("To verify the eligibility of a valid user using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 2, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityForEligibleUserV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the eligibility for user with no mobile in eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 3, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0NoMobile() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with invalid mobile in eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 4, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0InvalidMobile() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "123456789");
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with invalid mobile format in eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 5, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0ForInvalidMobileFormat() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "TestMobile");
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without email using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 6, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0ForEligibleUserWithoutEmail() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.emailRequired, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the eligibility of a  user without firstname using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 7, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0ForUserWithoutFirstName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_FNAME_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Something went wrong", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without last name using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 8, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0ForUserWithoutLastName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_LNAME_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Something went wrong", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without product details using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 9, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0WithoutProductDetails() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_PRODUCT_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_PRODUCT_SKU_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without address details using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 10, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0WithoutAddressDetails() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_ADDRESS_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_ADDRESS_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid amount using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 11, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0WithInvalidAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", "InvalidAmount");
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid currency using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 12, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0WithInvalidCurrency() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("currency", "USD");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_CURRENCY", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a blocked user using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 13, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0UserBlocked() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLOCKED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Your LazyPay account seems to be blocked", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_USER_BLOCKED", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility of a fraud blocked using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 14, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0UserFraud() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLACKLISTED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Your LazyPay account seems to be suspended", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Your LazyPay account seems to be suspended", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_BLACKLISTED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of locked user using eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 15, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0UserLocked() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOCKED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Your account seems to be locked", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Your account seems to be locked", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_LOCKED", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the  eligibility v0 API without signature")
    @Feature("EligibilityV0")
    @Test(priority = 16, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0WithoutSignature() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOCKED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("signature", " ");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_SIGNATURE_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for signature mismatch")
    @Feature("EligibilityV0")
    @Test(priority = 17, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0SignatureMismatch() throws Exception {

        signature = GenerateSignature.generateSignature(TransactionData.USER_MOBILE_LOCKED + TransactionData.USER_EMAIL
                + TransactionData.TRANSACTION_AMOUNT + TransactionData.CURRENCY, TransactionData.ACCESS_KEY_EMAIL_REQUIRED);

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_OPTED_OUT);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("signature", signature);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_SIGNATURE_MISMATCH", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for invalid merchant")
    @Feature("EligibilityV0")
    @Test(priority = 18, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0InvalidMerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_INVALID);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_ACCESS_KEY", "Failure reason could be different else check if the validation still exist in code or else check if the a merchant has been created with the same access key ");
    }

    @Description("To verify the eligibility v0 for maximum transaction limit")
    @Feature("EligibilityV0")
    @Test(priority = 19, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0MaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_PER_TXN_MAX_LIMIT", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, TransactionData.MAX_TXN_AMOUNT_ERROR_MSG, "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for amount > credit limit")
    @Feature("EligibilityV0")
    @Test(priority = 20, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0TxnAmtGTLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT_HIGH);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! The transaction value seems to be higher than your available LazyPay credit limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_EXCEEDS_USER_MAX_TXN_LIMIT", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility v0 for txn amount > balance amount")
    @Feature("EligibilityV0")
    @Test(priority = 21, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0TxnGTBalance() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_MAXED_OUT);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", String.valueOf(EligibilityV0Tests.availableLimitMaxedOutUser + 1));
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! The transaction value seems to be higher than your available LazyPay credit limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_EXCEEDS_USER_MAX_TXN_LIMIT", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility v0 for more than 10 custom params")
    @Feature("EligibilityV0")
    @Test(priority = 22, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV0CustomParamLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("customParamMaxLimit", String.valueOf(true));

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "CUSTOM_PARAMS_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for fraud domain, new user")
    @Feature("EligibilityV0")
    @Test(priority = 23, groups = {"eligibility", "transaction", "regression"})
    public void verifyFraudDomainV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL_FRAUD);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Something went wrong", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_FD_OLD", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility v0 for more than 10 custom params")
    @Feature("EligibilityV0")
    @Test(priority = 24, groups = {"eligibility", "transaction", "regression"})
    public void verifyCustomParamKeyLimitV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("customParamMaxLength", String.valueOf(true));

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "CUSTOM_PARAMS_KEY_LENGTH_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 without custom params")
    @Feature("EligibilityV0")
    @Test(priority = 25, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0WithoutCustomParams() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("noCustomParam", String.valueOf(true));

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Failure reason could be different else check if the user state has changed");
        //Due date validation
    }

    @Description("To verify the eligibility for non transacted user with valid details in eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 26, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityForEligibleNonTransactedUserV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_NON_TRANSACTED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the eligibility for new user with valid details in eligibility v0 API")
    @Feature("EligibilityV0")
    @Test(priority = 27, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityForEligibleNewUserV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_NEW);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the eligibility v0 is allowed for an amount less than maximum transaction limit")
    @Feature("EligibilityV0")
    @Test(priority = 28, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0LessThanMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the eligibility of a valid user using eligibility v0 API with source=CITRUS_SDK")
    @Feature("EligibilityV0")
    @Test(priority = 29, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0SourceCitrusSDK() {

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V0 response when source=CitrusSDK")
    @Feature("EligibilityV0")
    @Test(priority = 30, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0DataTypeValidationSourceCitrusSDK() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.signUpModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.autoDebit.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.merchantLogo.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.signUpModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.autoDebit.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.merchantLogo.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the eligibility of a valid user using eligibility v0 API with source=CITRUS_SSLV2")
    @Feature("EligibilityV0")
    @Test(priority = 31, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0SourceCitrusSSLV2() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V0 response when source=CitrusSSLV2")
    @Feature("EligibilityV0")
    @Test(priority = 32, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0DataTypeValidationSourceCitrusSSLV2() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.signUpModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.addressReq.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.firstNameReq.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.lastNameReq.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.signUpModes.getClass().getSimpleName(), ArrayList.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.addressReq.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.firstNameReq.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.lastNameReq.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the eligibility of a valid user using eligibility v0 API with source=STANDALONE_API")
    @Feature("EligibilityV0")
    @Test(priority = 33, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0SourceStandaloneAPI() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V0 response when source=STANDALONE_API")
    @Feature("EligibilityV0")
    @Test(priority = 34, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0DataTypeValidationSourceStandaloneAPI() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the eligibility of a valid user using eligibility v0 API with source=STANDALONE_REDIRECT")
    @Feature("EligibilityV0")
    @Test(priority = 35, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0SourceCitrusStandaloneRedirect() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V0 response when source=STANDALONE_REDIRECT")
    @Feature("EligibilityV0")
    @Test(priority = 36, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV0DataTypeValidationSourceStandaloneRedirect() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the 2FA enable=true for 2FA and deviceRule enabled merchant using eligibility v0 API when txnAmount>maxTxnAmount set in fraud")
    @Feature("EligibilityV0")
    @Test(priority = 37, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMaxTxnAmtEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID21);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT5);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the 2FA enable=false for 2FA and deviceRule enabled merchant using eligibility v0 API when txnAmount<maxTxnAmount set in fraud")
    @Feature("EligibilityV0")
    @Test(priority = 38, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMinTxnAmtEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID21);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the 2FA enable=False for 2FA and deviceRule enabled merchant using eligibility v0 API for trusted device and txnAmount<maxTxnAmount set in fraud")
    @Feature("EligibilityV0")
    @Test(priority = 39, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForTruestedDeviceEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID21);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("deviceId", TransactionData.TRUSTED_DEVICE1);
        request.put("platform", TransactionData.PLATFORM_NAME_ANDROID);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the 2FA enable=true for 2FA and deviceRule enabled merchant using eligibility v0 API for trusted device and txnAmount<maxTxnAmount set in fraud")
    @Feature("EligibilityV0")
    @Test(priority = 40, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForUnTruestedDeviceEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID21);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("deviceId", TransactionData.UNTRUSTED_DEVICE2);
        request.put("platform", TransactionData.PLATFORM_NAME_WEB);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the eligibility failed for new user for 2FA and deviceRule enabled merchant using eligibility v0 API when txnAmount>maxTxnAmount set in fraud")
    @Feature("EligibilityV0")
    @Test(priority = 41, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMaxTxnAmtEligibilityV0NewUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID40_NEWUSER);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT5);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! We don't seem to have LazyPay open for you as of now. But we promise to throw it open to you soon", "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_INELIGIBLE", "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the eligibility passed for new user for 2FA and deviceRule enabled merchant using eligibility v0 API when txnAmount<maxTxnAmount set in fraud and untrusted device")
    @Feature("EligibilityV0")
    @Test(priority = 42, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMinTxnAmtEligibilityV0NewUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID40_NEWUSER);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V0);
        request.put("deviceId", TransactionData.UNTRUSTED_DEVICE2);
        request.put("platform", TransactionData.PLATFORM_NAME_WEB);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }
}