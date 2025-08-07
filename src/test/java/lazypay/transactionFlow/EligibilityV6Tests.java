package lazypay.transactionFlow;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.CheckEligibility;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.EligibilityPojo;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static lazypay.transactionFlow.TransactionData.SUB_MERCHANT_ID_2FA_DeviceRule1;

public class EligibilityV6Tests {

    public static String signature;
    public static EligibilityPojo eligibilityPojo;

    @Description("To verify the eligibility of a valid user using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 1, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityForEligibleUserV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        //Due date validation
    }

    @Description("To verify the eligibility for user with valid details in eligibility v6 API")
    @Feature("EligibilityV6")
    @Test(priority = 2, groups = {"eligibility", "transaction", "sanity", "regression", "agg"})
    public void verifyEligibilityForEligibleTransactedUserV6() {

        Allure.addDescription("existingUser flag returned from eligibility response: " + CheckEligibility.eligibilityPojo.existingUser);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.existingUser, "Check that the user has transacted!");
    }

    @Description("To verify the eligibility for user with no mobile in eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 3, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6NoMobile() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with invalid mobile in eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 4, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6InvalidMobile() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "123456789");
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with invalid mobile format in eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 5, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6ForInvalidMobileFormat() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "TestMobile");
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without email using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 6, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6ForEligibleUserWithoutEmail() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("zip", TransactionData.ZIP);
        request.put("country", TransactionData.COUNTRY);
        request.put("street1", TransactionData.STREET1);
        request.put("street2", TransactionData.STREET2);
        request.put("state", TransactionData.STATE);
        request.put("city", TransactionData.CITY);
        request.put("firstName", "Testfirstname");
        request.put("lastName", "Testlastname");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        //Due date validation
    }

    @Description("To verify the eligibility of a  user without firstname using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 7, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6ForUserWithoutFirstName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("zip", TransactionData.ZIP);
        request.put("country", TransactionData.COUNTRY);
        request.put("street1", TransactionData.STREET1);
        request.put("street2", TransactionData.STREET2);
        request.put("state", TransactionData.STATE);
        request.put("city", TransactionData.CITY);
        request.put("lastName", "Testlastname");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without last name using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 8, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6ForUserWithoutLastName() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("zip", TransactionData.ZIP);
        request.put("country", TransactionData.COUNTRY);
        request.put("street1", TransactionData.STREET1);
        request.put("street2", TransactionData.STREET2);
        request.put("state", TransactionData.STATE);
        request.put("city", TransactionData.CITY);
        request.put("firstName", "Testlastname");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without product details using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 9, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6WithoutProductDetails() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_PRODUCT_REQUIRED);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_2FA_PRODUCT_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_PRODUCT_SKU_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user without address details using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 10, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6WithoutAddressDetails() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EMAIL_REQUIRED);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_EMAIL_REQUIRED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("firstName", "Testfirstname");
        request.put("lastName", "Testlastname");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_ADDRESS_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid amount using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 11, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6WithInvalidAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", "InvalidAmount");
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid currency using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 12, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6WithInvalidCurrency() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("currency", "USD");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_CURRENCY", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a blocked user using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 13, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6UserBlocked() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLOCKED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Your LazyPay account seems to be blocked", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_USER_BLOCKED", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility of a fraud blocked using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 14, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6UserFraud() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_BLACKLISTED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Your LazyPay account seems to be suspended", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_BLACKLISTED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of locked user using eligibility V6 API")
    @Feature("EligibilityV6")
    @Test(priority = 15, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6UserLocked() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOCKED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Your account seems to be locked", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_LOCKED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the  eligibility V6 API without signature")
    @Feature("EligibilityV6")
    @Test(priority = 16, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6WithoutSignature() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOCKED);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("signature", " ");

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_SIGNATURE_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility V6 for signature mismatch")
    @Feature("EligibilityV6")
    @Test(priority = 17, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6SignatureMismatch() throws Exception {

        signature= GenerateSignature.generateSignature(TransactionData.USER_MOBILE_LOCKED+TransactionData.USER_EMAIL
                +TransactionData.TRANSACTION_AMOUNT+TransactionData.CURRENCY, TransactionData.ACCESS_KEY_AGGREGATOR_2);

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_OPTED_OUT);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("signature", signature);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_SIGNATURE_MISMATCH", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility V6 for invalid merchant")
    @Feature("EligibilityV6")
    @Test(priority = 18, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6InvalidMerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_INVALID);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "401", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_ACCESS_KEY", "Failure reason could be different else check if the validation still exist in code or else check if the a merchant has been created with the same access key ");
    }

    @Description("To verify the eligibility V6 for maximum transaction limit")
    @Feature("EligibilityV6")
    @Test(priority = 19, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6MaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Eligibility declined. Transaction not permitted. Potential Fraud Risk", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_ELIGIBILITY_FAILURE_MAX_SUBMERCHANT_CAP_REACHED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility V6 for amount > credit limit")
    @Feature("EligibilityV6")
    @Test(priority = 20, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6TxnAmtGTLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT_HIGH);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Eligibility declined. Transaction not permitted. Potential Fraud Risk");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_ELIGIBILITY_FAILURE_MAX_SUBMERCHANT_CAP_REACHED", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility V6 for txn amount > balance amount")
    @Feature("EligibilityV6")
    @Test(priority = 21, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6TxnGTBalance() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_MAXED_OUT);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", String.valueOf(EligibilityV0Tests.availableLimitMaxedOutUser+1));
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! The transaction value seems to be higher than your available LazyPay credit limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_EXCEEDS_USER_MAX_TXN_LIMIT", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility V6 for more than 10 custom params")
    @Feature("EligibilityV6")
    @Test(priority = 22, groups = {"eligibility", "transaction", "regression"})
    public void verifyEligibilityV6CustomParamLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("customParamMaxLimit", String.valueOf(true));

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the user state has changed");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "CUSTOM_PARAMS_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility V6 for fraud domain, new user")
    @Feature("EligibilityV6")
    @Test(priority = 23, groups = {"eligibility", "transaction", "regression"})
    public void verifyFraudDomainV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL_FRAUD);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! Something went wrong");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_FD_OLD", "Failure reason could be different else check if the user state has changed");
    }

    @Description("To verify the eligibility v0 for more than 10 custom params")
    @Feature("EligibilityV6")
    @Test(priority = 24, groups = {"eligibility", "transaction", "regression"})
    public void verifyCustomParamKeyLimitV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("customParamMaxLength", String.valueOf(true));

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "CUSTOM_PARAMS_KEY_LENGTH_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for non transacted user with valid details in eligibility v6 API")
    @Feature("EligibilityV6")
    @Test(priority = 25, groups = {"eligibility", "transaction", "sanity", "regression", "agg"})
    public void verifyEligibilityForEligibleNonTransactedUserV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_NON_TRANSACTED);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Failure reason could be different else check if the user state has changed");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.existingUser, "Check that the user has not transacted!");
    }

    @Description("To verify the eligibility for user with invalid merchant ID in eligibility v6 API")
    @Feature("EligibilityV6")
    @Test(priority = 26, groups = {"eligibility", "transaction", "regression", "agg"})
    public void verifyEligibilityV6ForInvalidSubmerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", "InvalidSubMerchantID");
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Sub-merchant ID is not present on the LP system", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_INVALID_SUBMERCHANT_ID", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with empty submerchant ID in eligibility v6 API")
    @Feature("EligibilityV6")
    @Test(priority = 27, groups = {"eligibility", "transaction", "regression", "sanity", "agg"})
    public void verifyEligibilityV6ForEmptySubmerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", " ");
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Sub-merchant ID cannot be null or empty", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_BLANK_SUB_MERCHANT_ID", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with no submerchant ID in eligibility v6 API")
    @Feature("EligibilityV6")
    @Test(priority = 28, groups = {"eligibility", "transaction", "regression", "agg"})
    public void verifyEligibilityV6ForMissingSubmerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Sub-merchant ID missing in the received request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_MISSING_SUB_MERCHANT_ID", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v6 for disabled sub merchant")
    @Feature("EligibilityV6")
    @Test(priority = 29, groups = {"eligibility", "transaction", "regression", "agg"})
    public void verifyEligibilityV6ForDisabledSubmerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_DISABLED);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, "Sub-merchant is disabled on the LP system", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_SUB_MERCHANT_DISABLED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for new user with valid details in eligibility v6 API")
    @Feature("EligibilityV6")
    @Test(priority = 30, groups = {"eligibility", "transaction", "sanity", "regression", "agg"})
    public void verifyEligibilityForEligibleNewUserV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_NEW);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.existingUser, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v6 is allowed for an amount less than maximum transaction limit")
    @Feature("EligibilityV6")
    @Test(priority = 31, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6LessThanMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v6 for maximum transaction limit of direct merchants is applied on excluded merchant")
    @Feature("EligibilityV6")
    @Test(priority = 32, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6MaxTxnLimitExcludeCapAppliesDirectCap() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EXCLUDE_CAP);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_EXCLUDE_CAP);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibility.eligibilityPojo.status, "400");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.errorCode, "LP_PER_TXN_MAX_LIMIT", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.message, TransactionData.MAX_TXN_AMOUNT_ERROR_MSG, "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v6 for maximum transaction limit on aggregator is not applied on excluded merchant")
    @Feature("EligibilityV6")
    @Test(priority = 33, groups = {"eligibility", "transaction", "regression", "sanity"})
    public void verifyEligibilityV6MaxTxnLimitExcludeCap() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_ABOVE_MAX_AMOUNT_AGG);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_EXCLUDE_CAP);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_EXCLUDE_CAP);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a valid user using eligibility V6 API with source=CITRUS_SDK")
    @Feature("EligibilityV6")
    @Test(priority = 34,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6SourceCitrusSDK() {

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason,"Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code,"LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V6 response when source=CitrusSDK")
    @Feature("EligibilityV6")
    @Test(priority = 35,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6DataTypeValidationSourceCitrusSDK() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the eligibility of a valid user using eligibility V6 API with source=CITRUS_SSLV6")
    @Feature("EligibilityV6")
    @Test(priority = 36,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6SourceCitrusSSLV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_CITRUS_SSLV2);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason,"Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code,"LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V6 response when source=CitrusSSLV6")
    @Feature("EligibilityV6")
    @Test(priority = 37,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6DataTypeValidationSourceCitrusSSLV6() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the eligibility of a valid user using eligibility V6 API with source=STANDALONE_API")
    @Feature("EligibilityV6")
    @Test(priority = 38,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6SourceStandaloneAPI() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_API);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason,"Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code,"LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V6 response when source=STANDALONE_API")
    @Feature("EligibilityV6")
    @Test(priority = 39,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6DataTypeValidationSourceStandaloneAPI() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }
    @Description("To verify the eligibility of a valid user using eligibility V6 API with source=STANDALONE_REDIRECT")
    @Feature("EligibilityV6")
    @Test(priority = 40,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6SourceStandaloneRedirect() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID1);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_AGGREGATOR_1);
        request.put("subMerchant", TransactionData.SUB_MERCHANT_ID_1);
        request.put("source", TransactionData.SOURCE_STANDALONE_REDIRECT);
        request.put("version", TransactionData.ELIGIBILITY_V6);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason,"Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code,"LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the data type in eligibility V6 response when source=STANDALONE_REDIRECT")
    @Feature("EligibilityV6")
    @Test(priority = 41,groups = { "eligibility","transaction", "regression", "sanity"})
    public void verifyEligibilityV6DataTypeValidationSourceStandaloneRedirect() {

        Assert.assertEquals(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Assert.assertEquals(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());

        Allure.addAttachment(CheckEligibility.eligibilityPojo.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.reason.getClass().getSimpleName(), String.class.getSimpleName());
        //Allure.addAttachment(CheckEligibility.eligibilityPojo.message.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.code.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.userEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.existingUser.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Allure.addAttachment(CheckEligibility.eligibilityPojo.emailRequired.getClass().getSimpleName(), Boolean.class.getSimpleName());
    }

    @Description("To verify the 2FA enable=true for 2FA and deviceRule enabled merchant using eligibility V6 API when txnAmount>maxTxnAmount set in fraud")
    @Feature("EligibilityV6")
    @Test(priority = 42, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMaxTxnAmtEligibilityV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID29);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT5);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("subMerchant", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected.Please Check the txn amount and device info data");
    }

    @Description("To verify the 2FA enable=false for 2FA and deviceRule enabled merchant using eligibility V6 API when txnAmount<maxTxnAmount set in fraud")
    @Feature("EligibilityV6")
    @Test(priority = 43, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMinTxnAmtEligibilityV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID29);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("subMerchant", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected.Please Check the txn amount and device info data");
    }

    @Description("To verify the 2FA enable=False for 2FA and deviceRule enabled merchant using eligibility V6 API for trusted device and txnAmount<maxTxnAmount set in fraud")
    @Feature("EligibilityV6")
    @Test(priority = 44, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForTruestedDeviceEligibilityV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID29);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("subMerchant", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("deviceId", TransactionData.TRUSTED_DEVICE1);
        request.put("platform", TransactionData.PLATFORM_NAME_ANDROID);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected.Please Check the txn amount and device info data");
    }

    @Description("To verify the 2FA enable=true for 2FA and deviceRule enabled merchant using eligibility V6 API for trusted device and txnAmount<maxTxnAmount set in fraud")
    @Feature("EligibilityV6")
    @Test(priority = 45, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForUnTruestedDeviceEligibilityV6() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID21);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("subMerchant", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("deviceId", TransactionData.UNTRUSTED_DEVICE2);
        request.put("platform", TransactionData.PLATFORM_NAME_WEB);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the eligibility failed for new user for 2FA and deviceRule enabled merchant using eligibility V6 API when txnAmount>maxTxnAmount set in fraud")
    @Feature("EligibilityV6")
    @Test(priority = 46, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMaxTxnAmtEligibilityV6NewUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID40_NEWUSER);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT5);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("subMerchant", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertFalse(CheckEligibility.eligibilityPojo.txnEligibility, "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.userEligibility, "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Oops!! We don't seem to have LazyPay open for you as of now. But we promise to throw it open to you soon", "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_USER_INELIGIBLE", "Check the txnAmount, it should be less than>MaxTxnAmount set in fraud");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

    @Description("To verify the eligibility passed for new user for 2FA and deviceRule enabled merchant using eligibility V6 API when txnAmount<maxTxnAmount set in fraud and untrusted device")
    @Feature("EligibilityV6")
    @Test(priority = 47, groups = {"eligibility", "transaction", "regression", "sanity", "2FA"})
    public void verify2FAFlagValueWith2FAAndDeviceForMinTxnAmtEligibilityV6NewUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TransactionData.USER_MOBILE_VALID40_NEWUSER);
        request.put("email", TransactionData.USER_EMAIL);
        request.put("amount", TransactionData.TRANSACTION_AMOUNT);
        request.put("accessKey", TransactionData.ACCESS_KEY_2FA_DEVICERULE_MAgg);
        request.put("subMerchant", SUB_MERCHANT_ID_2FA_DeviceRule1);
        request.put("source", TransactionData.SOURCE_CITRUS_SDK);
        request.put("version", TransactionData.ELIGIBILITY_V6);
        request.put("deviceId", TransactionData.UNTRUSTED_DEVICE2);
        request.put("platform", TransactionData.PLATFORM_NAME_WEB);
        CheckEligibility.checkEligibilityMethod(request);
        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertFalse(CheckEligibility.eligibilityPojo.afarequired, "AFA required value is not as expected. Please Check the txn amount and device info data");
    }

}