package upi.bnpl;

import api.upi.bnpl.LPTransactionEligibility;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pojos.upi.bnpl.LPTransactionEligibilityPojo;
import upi.FindOrCreateMerchantSteps;
import upi.UPIConstants;
import upi.UPIData;
import util.StringTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LPTransactionEligibilitySteps {

    Date date = new Date();
    String timeNowMilli = String.valueOf(date.getTime());
    public static LPTransactionEligibilityPojo transactionEligibilityPojo = new LPTransactionEligibilityPojo();
    LPTransactionEligibility transactionEligibility = new LPTransactionEligibility();
    FindOrCreateMerchantSteps findOrCreateMerchantTests = new FindOrCreateMerchantSteps();

    public LPTransactionEligibilitySteps() throws Exception {
    }

    @BeforeMethod
    public void LPTransactionEligibilityTestsPrerequisites() throws Exception {
        findOrCreateMerchantTests.verifyFOCMerchantForValidMerchantUsingScanAndPay();

    }

    @Step
    public void verifyLPTransactionEligibilityUsingScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. response code has been changed by the developer");
        Assert.assertNotNull(transactionEligibilityPojo.getUuid(), "Check if user exists");

    }

    @Step
    public void verifyLPTransactionEligibilityUsingEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. response code has been changed by the developer");
        Assert.assertNotNull(transactionEligibilityPojo.getUuid(), "Check if user exists");

    }

    @Step
    public void verifyLPTransactionEligibilityUsingCollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. response code has been changed by the developer");
        Assert.assertNotNull(transactionEligibilityPojo.getUuid(), "Check if user exists");

    }

    @Step
    public void verifyLPTransactionEligibilityUsingIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.focMerchantUPIPojo.lpMerchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. response code has been changed by the developer");
        Assert.assertNotNull(transactionEligibilityPojo.getUuid(), "Check if user exists");

    }

    @Step
    public void verifyUserIneligibleForHigherAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", "1000000")
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_INELIGIBLE", "Check if user has limit equal to more than the input amount or if the response code has been changed by the developer");

    }

    @Step
    public void verifyLPTransactionEligibilityForInvalidMerchant() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.INVALID_MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_INELIGIBLE", "Make sure the merchant is not valid or if the response code has been changed by the developer");

    }

    @Step
    public void verifyLPTransactionEligibilityForIncorrectMerchant() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa", "abc")
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getResponseCode() ,"LP_INELIGIBLE", "Make sure the merchant VPA is incorrect or if the response code has been changed by the developer");

    }

    @Step
    public void verifyMandatoryValidationOnAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", "")
                .replace("merchantId", "")
                .replace("merchantVpa",  UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getErrorCode() ,"INVALID_FIELD", "Make sure that amount is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(transactionEligibilityPojo.getMessage() ,"Error in the field : amount", "Make sure that amount is not passed in the request or if the message has been changed by the developer");

    }

    @Step
    public void verifyMandatoryValidationOnMobile() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa",  UPIData.MERCHANT_VPA)
                .replace("userMobile", "")
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getErrorCode() ,"INVALID_FIELD", "Make sure that mobile is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(transactionEligibilityPojo.getMessage() ,"Error in the field : mobile", "Make sure that mobile is not passed in the request or if the message has been changed by the developer");

    }

    @Step
    public void verifyMandatoryValidationOnMerchantVPAForLPEligibility() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String lpTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa",  "")
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        transactionEligibilityPojo = transactionEligibility.post(queryParamDetails, headerDetails, lpTxnEligibilityUPIRequest);
        Assert.assertEquals(transactionEligibilityPojo.getErrorCode() ,"INVALID_FIELD", "Make sure that mobile is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(transactionEligibilityPojo.getMessage() ,"Error in the field : merchantParameters", "Make sure that mobile is not passed in the request or if the message has been changed by the developer");

    }
}


