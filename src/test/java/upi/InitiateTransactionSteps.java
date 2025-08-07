package upi;

import api.upi.bnpl.InitiateTransaction;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pojos.upi.bnpl.InitiateTransactionPojo;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class InitiateTransactionSteps {

    public static InitiateTransactionPojo initiateTransactionPojo = new InitiateTransactionPojo();
    InitiateTransaction initiateTransaction = new InitiateTransaction();
    public static String txnRefNo;

    public InitiateTransactionSteps() throws Exception {
    }

    @BeforeMethod
    public void InitiateTransactionTestsPrerequisites() throws Exception {

    }

    @Step
    public void verifyInitiateTransactionLPScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.INITIATE_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","LP")
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, postIntentRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction");
    }

    @Step
    public void verifyInitiateTransactionLPEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.INITIATE_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","LP")
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, postIntentRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction");
    }

    @Step
    public void verifyInitiateTransactionLPCollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.INITIATE_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","LP")
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, postIntentRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction");
    }

    @Step
    public void verifyInitiateTransactionLPIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String postIntentRequest = new StringTemplate(UPIConstants.INITIATE_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LP_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","LP")
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, postIntentRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction");
    }

    @Step
    public void verifyInitiateTransactionEMIScanAndPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String initiateTransactionRequest = new StringTemplate(UPIConstants.INITIATE_EMI_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","CL")
                .replace("emi",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).emi))
                .replace("interestRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestRate))
                .replace("firstInstallmentDate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).firstInstallmentDate))
                .replace("overdueRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).overdueRate))
                .replace("subventedAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).subventedAmount))
                .replace("loanPayableAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).loanPayableAmount))
                .replace("tenure",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).tenure.value))
                .replace("principalAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).principalAmount))
                .replace("processingFee",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).processingFee))
                .replace("interestPayable",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestPayable))
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_SCANPAY)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, initiateTransactionRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction and converting to EMI");
    }

    @Step
    public void verifyInitiateTransactionEMIEnteredVPA() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String initiateTransactionRequest = new StringTemplate(UPIConstants.INITIATE_EMI_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","CL")
                .replace("emi",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).emi))
                .replace("interestRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestRate))
                .replace("firstInstallmentDate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).firstInstallmentDate))
                .replace("overdueRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).overdueRate))
                .replace("subventedAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).subventedAmount))
                .replace("loanPayableAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).loanPayableAmount))
                .replace("tenure",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).tenure.value))
                .replace("principalAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).principalAmount))
                .replace("processingFee",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).processingFee))
                .replace("interestPayable",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestPayable))
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_ENTEREDVPA)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, initiateTransactionRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction and converting to EMI");
    }

    @Step
    public void verifyInitiateTransactionEMICollectRequest() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String initiateTransactionRequest = new StringTemplate(UPIConstants.INITIATE_EMI_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","CL")
                .replace("emi",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).emi))
                .replace("interestRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestRate))
                .replace("firstInstallmentDate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).firstInstallmentDate))
                .replace("overdueRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).overdueRate))
                .replace("subventedAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).subventedAmount))
                .replace("loanPayableAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).loanPayableAmount))
                .replace("tenure",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).tenure.value))
                .replace("principalAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).principalAmount))
                .replace("processingFee",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).processingFee))
                .replace("interestPayable",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestPayable))
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_COLLECTREQUEST)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, initiateTransactionRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction and converting to EMI");
    }

    @Step
    public void verifyInitiateTransactionEMIIntentPay() {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("device-data", UPIData.DEVICE_DATA);

        String initiateTransactionRequest = new StringTemplate(UPIConstants.INITIATE_EMI_TRANSACTION)
                .replace("currency", UPIData.CURRENCY)
                .replace("amount", UPIData.LOAN_AMOUNT)
                .replace("deviceId", UPIData.REGISTERED_DEVICE_ID)
                .replace("merchantVpa", FindOrCreateMerchantSteps.focMerchantUPIPojo.merchantVpa)
                .replace("mobile",UPIData.REGISTERED_USER)
                .replace("type","CL")
                .replace("emi",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).emi))
                .replace("interestRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestRate))
                .replace("firstInstallmentDate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).firstInstallmentDate))
                .replace("overdueRate",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).overdueRate))
                .replace("subventedAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).subventedAmount))
                .replace("loanPayableAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).loanPayableAmount))
                .replace("tenure",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).tenure.value))
                .replace("principalAmount",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).principalAmount))
                .replace("processingFee",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).processingFee))
                .replace("interestPayable",String.valueOf(PostIntentSteps.postIntentPojo.eligibilityResponse.clPlans.get(0).interestPayable))
                .replace("acquisitionChannel", UPIData.ACQUISITION_CHANNEL_INTENTPAY)
                .replace("simId",UPIData.REGISTERED_SIM_ID)
                .replace("requestId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .replace("upiTxnIntentId",String.valueOf(PostIntentSteps.postIntentPojo.upiTxnIntentId))
                .toString();

        initiateTransactionPojo = initiateTransaction.post(queryParamDetails, headerDetails, initiateTransactionRequest);
        txnRefNo = initiateTransactionPojo.txnRefNo;
        Assert.assertNotNull(txnRefNo, "Make sure user is eligible for transaction and converting to EMI");
    }
}
