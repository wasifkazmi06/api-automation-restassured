package mpl;

import api.vaultTM.BalanceCache;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import lazypay.CheckEligibility;
import lazypay.MakeRepayment;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.vaultTM.BalanceCachePojo;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EligibilityV0Test {

    MakeRepayment makeRepayment = new MakeRepayment("", 0, "");

    public BalanceCache balanceCache = new BalanceCache();
    public BalanceCachePojo balanceCachePojo = new BalanceCachePojo();
    public String accountIdMaxedOutUser;
    public static double availableLimitMaxedOutUser;

    public EligibilityV0Test() throws Exception {
    }

    @Description("To set up test data for MPL automation suite")
    @Feature("MPL_User_Data_Setup")
    @Test(priority = 1, dataProvider = "mpl-user", dataProviderClass = mpl.TestData.class, groups = {"mpl_regression"})
    public void sanityDataSetUp(String mobile) throws Exception {

        if(mobile.equals(TestData.USER_MOBILE_VALID_1)){
            makeRepayment.verifyRepayDetails(mobile);
            if(makeRepayment.repayDetailsPojo.dueAmount>1 || makeRepayment.repayDetailsPojo.totalOutstanding > 10000) {
                makeRepayment.makeRepaymentMethod(mobile);
            }
        }

        if(mobile.equals(TestData.USER_MOBILE_MAXED_OUT)) {
            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();

            accountIdMaxedOutUser = TransactionData.getAccountDetails(TestData.USER_MOBILE_MAXED_OUT, TestData.TENANT_MEESHO,"account_id");
            balanceCachePojo = balanceCache.get(queryParamDetails, headerDetails, accountIdMaxedOutUser);
            availableLimitMaxedOutUser = (balanceCachePojo.defaultCommitted.amount + balanceCachePojo.defaultPendingOutgoing.amount) * -1;
        }

        if(mobile.equals(TestData.USER_MOBILE_NEW_TXN)){
            if(TransactionData.getAccountDetails(TestData.USER_MOBILE_NEW_TXN, TestData.TENANT_MEESHO,"account_id") != null){
                TransactionData.deleteUser(TestData.USER_MOBILE_NEW_TXN);
            }
        }

        TransactionData.updateRestriction(mobile, "");
    }

    @Description("To verify the eligibility for MPL of a valid user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 2, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0ExistingUser() throws Exception {

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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, TestData.REASON_ELIGIBLE);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, TestData.CODE_ELIGIBLE);
        //availableLimit
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the data type in eligibility V0 response")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 3, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyDataTypeValidationEligibilityV0() {

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.txnEligibility.getClass().getSimpleName(), Boolean.class.getSimpleName());
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.availableLimit.getClass().getSimpleName(), Double.class.getSimpleName());
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId.getClass().getSimpleName(), String.class.getSimpleName());
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.customParams.getClass().getSimpleName(), LinkedHashMap.class.getSimpleName());
    }

    @Description("To verify the eligibility for MPL of a valid user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 4, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0NewUser() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_NEW);
        request.put("email", TestData.USER_EMAIL_NEW);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, TestData.REASON_ELIGIBLE);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, TestData.CODE_ELIGIBLE);
        //availableLimit
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 5, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyIneligibleUserEligibilityV0() throws Exception {

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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, "Oops!! We don't seem to have Mpl open for you as of now. But we promise to throw it open to you soon");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, "MPL_USER_INELIGIBLE");
        Assert.assertEquals(String.valueOf(CheckEligibilityStep.eligibilityPOJO.availableLimit), "0.0");
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 6, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyExistingLazypayUserMeeshoEligibilityV0() throws Exception {

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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, "Sign up is required");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, "MPL_SIGNUP_REQUIRED");
        Assert.assertEquals(String.valueOf(CheckEligibilityStep.eligibilityPOJO.availableLimit), "0.0");
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 7, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyExistingMeeshoUserLazypayEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_1);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
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
        request.put("version", TransactionData.ELIGIBILITY_V2);

        CheckEligibility.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibility.eligibilityPojo.txnEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertTrue(CheckEligibility.eligibilityPojo.userEligibility, "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.reason, "Hola!! Avail LazyPay Credit with just an OTP", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
        Assert.assertEquals(CheckEligibility.eligibilityPojo.code, "LP_ELIGIBLE", "Check that the user is correctly whitelisted from DS and has sufficient available limit");
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 8, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0UserBlocked() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_BLOCKED);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 403, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Your Mpl account seems to be blocked.", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_USER_BLOCKED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 9, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0UserFraud() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_BLACKLISTED);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 403, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Your Mpl account seems to be suspended", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_USER_BLACKLISTED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 10, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0UserLocked() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_LOCKED);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 403, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Your Mpl account seems to be blocked.", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_USER_BLOCKED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 11, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0TxnAmtGTLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_LOW_CREDIT_LIMIT);
        request.put("email", TestData.USER_EMAIL_VALID_1);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, "Sorry, you do not have sufficient balance for this transaction", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, "MPL_INSUFFICIENT_BALANCE", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 12, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void txnAmountGreaterThanAvailableLimitEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_MAXED_OUT);
        request.put("amount", String.valueOf(availableLimitMaxedOutUser + 1));
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, "Sorry, you do not have sufficient balance for this transaction", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, "MPL_INSUFFICIENT_BALANCE", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    //@Test verifyEligibilityV0TxnGTBalance

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 13, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0GreaterThanMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_1);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertFalse(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, "Merchant transaction limit exceeded", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, "MPL_EXCEEDS_MAX_TXN_LIMIT", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 14, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0EqualToMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_1);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, TestData.REASON_ELIGIBLE);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, TestData.CODE_ELIGIBLE);
        //availableLimit
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 15, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0LessThanMaxTxnLimit() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_1);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, TestData.REASON_ELIGIBLE);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, TestData.CODE_ELIGIBLE);
        //availableLimit
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.customParams);
    }

    @Description("To verify the eligibility for BNPL Meesho of a blocked user using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 16, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0ForNonMeeshoMerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_1);
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TransactionData.ACCESS_KEY);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 403, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Forbidden", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! You do not seem to have access", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_ACCESS_DENIED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a user without email using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
   @Test(priority = 17, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyIncorrectEmailFormatEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", "incorrectEmail");
        request.put("amount", TestData.TRANSACTION_AMOUNT_DEFAULT);
        request.put("accessKey", TestData.ACCESS_KEY_MPL_EMAIL_ADDRESS_REQUIRED);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Entered email seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_EMAIL", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility with no mobile passed in eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 18, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyNoMobileEligibilityV0() throws Exception {

        HashMap<String, String> request = new HashMap<>();
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Mandatory user name parameters required", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_USER_DETAILS_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with invalid mobile in eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 19, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0InvalidMobile() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "123456789");
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility for user with invalid mobile format in eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 20, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0ForInvalidMobileFormat() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", "TestMobile");
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Entered mobile number seems to be invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_MOBILE", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid amount using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 21, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0WithInvalidAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid amount using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 22, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0WithAmountMoreThan2DecimalPlace() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
        request.put("amount", "123.456");
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility of a  user with invalid currency using eligibility v0 API")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 23, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0WithInvalidCurrency() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
        request.put("amount", "123.456");
        request.put("currency", "USD");
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Currency is invalid", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_CURRENCY", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the  eligibility v0 API without signature")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 24, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0WithoutSignature() throws Exception {

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
        request.put("signature", " ");
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Signature is missing", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_SIGNATURE_REQUIRED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for signature mismatch")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 25, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0SignatureMismatch() throws Exception {

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
        request.put("signature", "IncorrectSignature");
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 401, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "There is mismatch in the signature", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_SIGNATURE_MISMATCH", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for invalid merchant")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 26, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0InvalidMerchant() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
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
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 401, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Unauthorized", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! You do not seem to have access", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_ACCESS_DENIED", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for more than 10 custom params")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 27, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyEligibilityV0CustomParamLimit() throws Exception {

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
        request.put("customParamMaxLimit", String.valueOf(true));
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "CUSTOM_PARAMS_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 for more than 10 custom params")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 28, groups = {"eligibility", "transaction", "mpl_regression"})
    public void verifyCustomParamKeyLimitV0() throws Exception {

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
        request.put("customParamMaxLength", String.valueOf(true));
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Oops!! Something went wrong", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "CUSTOM_PARAMS_KEY_LENGTH_EXCEEDS_LIMIT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 without custom params")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 29, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0WithoutCustomParams() throws Exception {

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
        request.put("noCustomParam", String.valueOf(true));
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertTrue(CheckEligibilityStep.eligibilityPOJO.txnEligibility);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.reason, TestData.REASON_ELIGIBLE);
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.code, TestData.CODE_ELIGIBLE);
        //availableLimit
        Assert.assertNotNull(CheckEligibilityStep.eligibilityPOJO.eligibilityResponseId);
        Assert.assertEquals(String.valueOf(CheckEligibilityStep.eligibilityPOJO.customParams), "{}" );
    }

    @Description("To verify the eligibility v0 without custom params")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 30, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0ZeroAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
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
        request.put("noCustomParam", String.valueOf(true));
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 without custom params")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 31, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0NegativeAmount() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
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
        request.put("noCustomParam", String.valueOf(true));
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }

    @Description("To verify the eligibility v0 without custom params")
    @Feature("MPL_EligibilityV0")
    @Test(priority = 32, groups = {"eligibility", "transaction", "mpl_regression", "sanity"})
    public void verifyEligibilityV0LessThanOne() throws Exception {

        HashMap<String, String> request = new HashMap<>();
        request.put("mobile", TestData.USER_MOBILE_VALID_2);
        request.put("email", TestData.USER_EMAIL_VALID_2);
        request.put("amount", "0.99");
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
        request.put("noCustomParam", String.valueOf(true));
        request.put("version", TransactionData.ELIGIBILITY_V0);

        CheckEligibilityStep.checkEligibilityMethod(request);

        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.status, 400, "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.error, "Bad Request", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.message, "Invalid amount or amount doesn’t have 2 decimal precision", "Failure reason could be different else check if the validation still exist in code");
        Assert.assertEquals(CheckEligibilityStep.eligibilityPOJO.errorCode, "MPL_INVALID_AMOUNT", "Failure reason could be different else check if the validation still exist in code");
    }
}
