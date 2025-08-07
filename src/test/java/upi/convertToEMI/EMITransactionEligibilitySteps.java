package upi.convertToEMI;

import api.upi.convertToEMI.EMITransactionEligibility;
import io.qameta.allure.Step;
import org.testng.Assert;
import pojos.upi.convertToEMI.EMITransactionEligibilityPojo;
import upi.FindOrCreateMerchantSteps;
import upi.UPIConstants;
import upi.UPIData;
import util.StringTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EMITransactionEligibilitySteps {

    Date date = new Date();
    String timeNowMilli = String.valueOf(date.getTime());
    EMITransactionEligibilityPojo emiTransactionEligibilityPojo = new EMITransactionEligibilityPojo();
    EMITransactionEligibility emiTransactionEligibility = new EMITransactionEligibility();

    public EMITransactionEligibilitySteps() throws Exception {
    }

    @Step
    public void verifyEMITransactionEligibilityUsingScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.merchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();


        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);

        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. user is whitelisted for EMI in UPI service\n4. response code has been changed by the developer");
        Assert.assertNotNull(emiTransactionEligibilityPojo.getUuid(), "Check if user exists, for uuid");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).principalAmount, UPIData.LOAN_AMOUNT, "Make sure the loan amount is correct and equal to the loan principal amount");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).tenure.value, UPIData.LOAN_TENURE, "Make sure the loan tenure passed in the request is correct");
    }

    @Step
    public void verifyEMITransactionEligibilityUsingEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.merchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. user is whitelisted for EMI in UPI service\n4. response code has been changed by the developer");
        Assert.assertNotNull(emiTransactionEligibilityPojo.getUuid(), "Check if user exists, for uuid");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).principalAmount, UPIData.LOAN_AMOUNT, "Make sure the loan amount is correct and equal to the loan principal amount");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).tenure.value, UPIData.LOAN_TENURE, "Make sure the loan tenure passed in the request is correct");
    }

    @Step
    public void verifyEMITransactionEligibilityUsingCollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.merchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. user is whitelisted for EMI in UPI service\n4. response code has been changed by the developer");
        Assert.assertNotNull(emiTransactionEligibilityPojo.getUuid(), "Check if user exists, for uuid");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).principalAmount, UPIData.LOAN_AMOUNT, "Make sure the loan amount is correct and equal to the loan principal amount");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).tenure.value, UPIData.LOAN_TENURE, "Make sure the loan tenure passed in the request is correct");
    }

    @Step
    public void verifyEMITransactionEligibilityUsingIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", FindOrCreateMerchantSteps.merchantId)
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_ELIGIBLE", "Check if the \n1. user has limit left \n2. user is not blocked \n3. user is whitelisted for EMI in UPI service\n4. response code has been changed by the developer");
        Assert.assertNotNull(emiTransactionEligibilityPojo.getUuid(), "Check if user exists, for uuid");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).principalAmount, UPIData.LOAN_AMOUNT, "Make sure the loan amount is correct and equal to the loan principal amount");
        Assert.assertEquals(emiTransactionEligibilityPojo.tenureInfoMap.get(0).tenure.value, UPIData.LOAN_TENURE, "Make sure the loan tenure passed in the request is correct");

    }

    @Step
    public void verifyUserIneligibleForHigherAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", "1000000")
                .replace("merchantId","")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_INELIGIBLE", "Check if user has limit equal to more than the input amount or if the response code has been changed by the developer");
        Assert.assertEquals(emiTransactionEligibilityPojo.getErrorCode(), "LP_EXCEEDS_USER_MAX_TXN_LIMIT", "Check if user has limit equal to more than the input amount or if the error code has been changed by the developer");

    }

    @Step
    public void verifyEMITransactionEligibilityForInvalidMerchant() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.INVALID_MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_INELIGIBLE", "Make sure the merchant is not valid or if the response code has been changed by the developer");
        Assert.assertEquals(emiTransactionEligibilityPojo.getErrorCode(), "MERCHANT_NOT_VALID", "Make sure the merchant is not valid or if the error code has been changed by the developer");

    }

    @Step
    public void verifyEMITransactionEligibilityForIncorrectMerchant() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa", "abc")
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getResponseCode() ,"CL_INELIGIBLE", "Make sure the merchant VPA is incorrect or if the response code has been changed by the developer");
        Assert.assertEquals(emiTransactionEligibilityPojo.getErrorCode(), "MERCHANT_NOT_VALID", "Make sure the merchant VPA is incorrect or if the error code has been changed by the developer");

    }


    @Step
    public void verifyMandatoryValidationOnAmount() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", "")
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getMessage() ,"Error in the field : amount", "Make sure that amount is not passed in the request");
        Assert.assertEquals(emiTransactionEligibilityPojo.getErrorCode(), "INVALID_FIELD", "Make sure that amount is not passed in the request or if the error code has been changed by the developer");

    }

    @Step
    public void verifyMandatoryValidationOnMobile() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa", UPIData.MERCHANT_VPA)
                .replace("userMobile", "")
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getErrorCode() ,"INVALID_FIELD", "Make sure that mobile is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(emiTransactionEligibilityPojo.getMessage() ,"Error in the field : mobile", "Make sure that mobile is not passed in the request or if the message has been changed by the developer");

    }

    @Step
    public void verifyMandatoryValidationOnMerchantVPAForEMIEligibility() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String clTxnEligibilityUPIRequest = new StringTemplate(UPIConstants.LP_TXN_ELIGIBILITY)
                .replace("date", timeNowMilli)
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("merchantId", "")
                .replace("merchantVpa", "")
                .replace("userMobile", UPIData.REGISTERED_USER)
                .toString();

        emiTransactionEligibilityPojo = emiTransactionEligibility.post(queryParamDetails, headerDetails, clTxnEligibilityUPIRequest);
        Assert.assertEquals(emiTransactionEligibilityPojo.getErrorCode() ,"INVALID_FIELD", "Make sure that mobile is not passed in the request or if the error code has been changed by the developer");
        Assert.assertEquals(emiTransactionEligibilityPojo.getMessage() ,"Error in the field : merchantParameters", "Make sure that mobile is not passed in the request or if the message has been changed by the developer");

    }
}
