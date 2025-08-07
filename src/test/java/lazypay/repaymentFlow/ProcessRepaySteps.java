package lazypay.repaymentFlow;

import api.lazypay.repayment.ProcessRepay;
import io.qameta.allure.Step;
import lazypay.LazypayConstants;
import org.testng.Assert;
import pojos.lazypay.repaymentFlow.*;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;

public class ProcessRepaySteps {
    ProcessRepayPojo processRepayPojo = new ProcessRepayPojo();
    ProcessRepay processRepay = new ProcessRepay();
    private PreferredMethodSteps preferredMethodTests;

    public ProcessRepaySteps() throws Exception {
    }

    @Step
    public void verifyProcessRepayUPI()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("mode", preferredMethodTests.preferredMethodPojo.paymentMethod )
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardToken", "")
                .replace("transactionId", InitiateRepaySteps.initiateRepayPojo.transactionId)
                .replace("status", "SUCCESS")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, InitiateRepaySteps.initiateRepayPojo.transactionId);
        Assert.assertNotNull(processRepayPojo.txnId);
        Assert.assertEquals(processRepayPojo.txnStatus,"SUCCESS","Process Repayment for a UPI repayment failed as txnStatus is not SUCCESS");
        Assert.assertNotNull(processRepayPojo.amount,"Process Repayment for a UPI repayment failed as Transaction amount is empty");

    }

    @Step
    public void verifyProcessRepayCreditCard()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("mode", PreferredMethodSteps.preferredMethodPojo.paymentMethod )
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardToken", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardToken)
                .replace("transactionId", InitiateRepaySteps.initiateRepayPojo.transactionId)
                .replace("status", "SUCCESS")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, InitiateRepaySteps.initiateRepayPojo.transactionId);
        Assert.assertNotNull(processRepayPojo.txnId);
        Assert.assertEquals(processRepayPojo.txnStatus,"SUCCESS","Process Repayment for a Credit card repayment failed as txnStatus is not SUCCESS");
        Assert.assertNotNull(processRepayPojo.amount,"Process Repayment for a Credit Card repayment failed as Transaction amount is empty");

    }

    @Step
    public void verifyProcessRepayDebitCard()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("mode", PreferredMethodSteps.preferredMethodPojo.paymentMethod )
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardToken", SavedOptionsSteps.savedOptionPojo.cards.get(0).cardToken)
                .replace("transactionId", InitiateRepaySteps.initiateRepayPojo.transactionId)
                .replace("status", "SUCCESS")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, InitiateRepaySteps.initiateRepayPojo.transactionId);
        Assert.assertNotNull(processRepayPojo.txnId);
        Assert.assertEquals(processRepayPojo.txnStatus,"SUCCESS","Process Repayment for a Debit Card repayment failed as txnStatus is not SUCCESS");
        Assert.assertNotNull(processRepayPojo.amount,"Process Repayment for a Debit card repayment failed as Transaction amount is empty");

    }

    @Step
    public void verifyProcessRepayNetBanking()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("mode", PreferredMethodSteps.preferredMethodPojo.paymentMethod )
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardToken", "")
                .replace("transactionId", InitiateRepaySteps.initiateRepayPojo.transactionId)
                .replace("status", "SUCCESS")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, InitiateRepaySteps.initiateRepayPojo.transactionId);
        Assert.assertNotNull(processRepayPojo.txnId);
        Assert.assertEquals(processRepayPojo.txnStatus,"SUCCESS","Process Repayment for a Net Banking repayment failed as txnStatus is not SUCCESS");
        Assert.assertNotNull(processRepayPojo.amount,"Process Repayment for a Net Banking repayment failed as Transaction amount is empty");

    }

    @Step
    public void verifyProcessRepayInvalidTransactionId()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", "10.0")
                .replace("mode", "upi")
                .replace("bankCode", "upi")
                .replace("cardToken", "")
                .replace("transactionId", "TXN101InvalidTxnId")
                .replace("status", "SUCCESS")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, "TXN101InvalidTxnId");
        Assert.assertEquals(processRepayPojo.message,"Transaction not found", "Process repay when invalid Transaction id is given in body failed as Transaction was not found");
        Assert.assertEquals(processRepayPojo.error, "Bad Request", "Process repay when invalid Transaction id is given in body failed because of Bad Request");

    }

    @Step
    public void verifyProcessRepayNoDetailsInBody()  {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", "")
                .replace("mode", "")
                .replace("bankCode", "")
                .replace("cardToken", "")
                .replace("transactionId", "")
                .replace("status", "")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, "TXN101InvalidTxnId");
        Assert.assertEquals(processRepayPojo.message,"Mandatory TxnId, status, amount and pgId params are missing in repayment request","process repay when TxnId, status, amount and pgId params are null failed as the values are not null");
        Assert.assertEquals(processRepayPojo.errorCode,"PAYUBIZ_CALLBACK_INSUFFICIENT_MANDATORY_PARAMS","process repay when TxnId, status, amount and pgId params are null failed as error code was mis-matched");

    }

    @Step
    public void verifyProcessRepayFailed()  {
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        String processRepayRequest= new StringTemplate(LazypayConstants.PROCESS_REPAY_REQUEST)
                .replace("amount", String.valueOf(RepayDetailsSteps.repayDetailsPojo.totalOutstanding))
                .replace("mode", PreferredMethodSteps.preferredMethodPojo.paymentMethod )
                .replace("bankCode", PreferredMethodSteps.preferredMethodPojo.bankCode)
                .replace("cardToken", "")
                .replace("transactionId", InitiateRepaySteps.initiateRepayPojo.transactionId)
                .replace("status", "FAIL")
                .toString();
        processRepayPojo = processRepay.postWithPathParams(queryParamDetails, headerDetails,processRepayRequest, InitiateRepaySteps.initiateRepayPojo.transactionId);
        Assert.assertNotNull(processRepayPojo.txnId);
        Assert.assertEquals(processRepayPojo.txnStatus,"IN_PROGRESS","Process Repayment for a unsuccessful repayment failed as txnStatus is not IN_PROGRESS");
        Assert.assertNotNull(processRepayPojo.amount,"Process Repayment for a unsuccessful repayment failed as Transaction amount is empty");

    }
}
