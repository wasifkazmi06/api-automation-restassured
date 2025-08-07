package lazypay.loanCancellationFlow;

import api.lazypay.loanCancellation.LoanCancellation;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.lazypay.loanCancellationFlow.LoanCancellationPojo;
import upi.UPIConstants;
import upi.UPIData;
import upi.ValidateTransactionSteps;
import upi.convertToEMI.EMIUPITransactionalFlowTests;
import util.StringTemplate;

import java.util.HashMap;
import java.util.Map;

public class LoanCancellationTests {
    public LoanCancellationTests() throws Exception {
    }
    EMIUPITransactionalFlowTests emiupiTransactionalFlowTests = new EMIUPITransactionalFlowTests();
    LoanCancellation loanCancellation = new LoanCancellation();

    @BeforeClass
    public void loanCancellationPrerequisites() throws Exception {
        emiupiTransactionalFlowTests.verifyEMIUPITransactionFlowUsingScanAndPay();
    }

    @Description("To verify the loan is not cancelled for incorrect transaction with valid user")
    @Feature("LoanCancellationNegativeTestCases")
    @Test(priority = 1,groups = { "sanity", "regression"})
    public void verifyLoanCancellationForIncorrectTxn() throws Exception {
        int count = 0;
        LoanCancellationPojo loanCancellationPojo = new LoanCancellationPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String loanCancellationRequest= new StringTemplate(UPIConstants.LOAN_CANCELLATION_REQUEST)
                .replace("saleTxnId", "TXN1030620121212")
                .replace("email", "")
                .replace("mobile", UPIData.REGISTERED_USER)
                .replace("cancellationAmount", String.valueOf(ValidateTransactionSteps.validateTransactionPojo.amount.value))
                .toString();

        while(count <3) {
            loanCancellationPojo = loanCancellation.post(queryParamDetails, headerDetails, loanCancellationRequest);
            if (loanCancellationPojo.errorCode == null
                    || !loanCancellationPojo.errorCode.equalsIgnoreCase("CAN_REFUND_AFTER_WHILE"))
                break;
            count++;
        }

        Assert.assertEquals(loanCancellationPojo.status,"400", "Check if transaction Id is mapped to the user or service is down");
        Assert.assertEquals(loanCancellationPojo.message,"Something went wrong", "Check if transaction Id is mapped to the user or service is down");

    }

    @Description("To verify the loan is not cancelled for incorrect cancellation amount having valid txn")
    @Feature("LoanCancellationNegativeTestCases")
    @Test(priority = 2,groups = { "sanity", "regression"})
    public void verifyLoanCancellationForIncorrectCancellationAmt() throws Exception {
        int count = 0;
        LoanCancellationPojo loanCancellationPojo = new LoanCancellationPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String loanCancellationRequest= new StringTemplate(UPIConstants.LOAN_CANCELLATION_REQUEST)
                .replace("saleTxnId", util.BitConversion.bitDecoder(ValidateTransactionSteps.validateTransactionPojo.txnRefNo))
                .replace("email", "")
                .replace("mobile", UPIData.REGISTERED_USER)
                .replace("cancellationAmount", "10001000.27")
                .toString();

        while(count <3) {
            loanCancellationPojo = loanCancellation.post(queryParamDetails, headerDetails, loanCancellationRequest);
            if (loanCancellationPojo.errorCode == null
                    || !loanCancellationPojo.errorCode.equalsIgnoreCase("CAN_REFUND_AFTER_WHILE"))
                break;
            count++;
        }

        Assert.assertEquals(loanCancellationPojo.status,"400", "Check if cancellation amount is correct or service is down");
        Assert.assertEquals(loanCancellationPojo.message,"Cancellation amount is invalid. ", "Check if cancellation amount is correct or service is down");

    }

    @Description("To verify the loan is cancelled for user")
    @Feature("LoanCancellationE2E")
    @Test(priority = 3,groups = { "sanity", "regression"})
    public void verifyLoanCancellation() throws Exception {
        int count = 0;
        LoanCancellationPojo loanCancellationPojo = new LoanCancellationPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String loanCancellationRequest= new StringTemplate(UPIConstants.LOAN_CANCELLATION_REQUEST)
                .replace("saleTxnId", util.BitConversion.bitDecoder(ValidateTransactionSteps.validateTransactionPojo.txnRefNo))
                .replace("email", "")
                .replace("mobile", UPIData.REGISTERED_USER)
                .replace("cancellationAmount", String.valueOf(ValidateTransactionSteps.validateTransactionPojo.amount.value))
                .toString();

        while(count <3) {
            loanCancellationPojo = loanCancellation.post(queryParamDetails, headerDetails, loanCancellationRequest);
            if (loanCancellationPojo.errorCode == null
                    || !loanCancellationPojo.errorCode.equalsIgnoreCase("CAN_REFUND_AFTER_WHILE"))
                break;
            count++;
        }

        Assert.assertEquals(loanCancellationPojo.settlementAmount,"0.0", "Check if the UPI transaction amount and cancellation amount is not same");
        Assert.assertEquals(loanCancellationPojo.cancellationStatus,"true", "Check if input is given correct or service is not down");
        Assert.assertEquals(loanCancellationPojo.loanRefundAmount,UPIData.LOAN_AMOUNT, "Check if the cancellation amount provided is not same as UPI transaction amount");
    }


    @Description("To verify the loan is not cancelled for incorrect mobile having valid txn")
    @Feature("LoanCancellationNegativeTestCases")
    @Test(priority = 4,groups = { "sanity", "regression"})
    public void verifyLoanCancellationForIncorrectMobile() throws Exception {
        int count = 0;
        LoanCancellationPojo loanCancellationPojo = new LoanCancellationPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        String loanCancellationRequest= new StringTemplate(UPIConstants.LOAN_CANCELLATION_REQUEST)
                .replace("saleTxnId", util.BitConversion.bitDecoder(ValidateTransactionSteps.validateTransactionPojo.txnRefNo))
                .replace("email", "")
                .replace("mobile", "6000000281")
                .replace("cancellationAmount", String.valueOf(ValidateTransactionSteps.validateTransactionPojo.amount.value))
                .toString();

        while(count <3) {
            loanCancellationPojo = loanCancellation.post(queryParamDetails, headerDetails, loanCancellationRequest);
            if (loanCancellationPojo.errorCode == null
                    || !loanCancellationPojo.errorCode.equalsIgnoreCase("CAN_REFUND_AFTER_WHILE"))
                break;
            count++;
        }

        Assert.assertEquals(loanCancellationPojo.status,"400", "Check if user mobile number is associate with current transaction or service is down");
        Assert.assertEquals(loanCancellationPojo.message,"Loan account is associated with different user", "Check if user mobile number is associate with current transaction or service is down");
    }

    @Description("To verify the loan is not cancelled for valid txn having mobile number field empty")
    @Feature("LoanCancellationNegativeTestCases")
    @Test(priority = 5,groups = { "sanity", "regression"})
    public void verifyLoanCancellationForMobileNull() throws Exception {
        int count = 0;
        LoanCancellationPojo loanCancellationPojo = new LoanCancellationPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        String loanCancellationRequest= new StringTemplate(UPIConstants.LOAN_CANCELLATION_REQUEST)
                .replace("saleTxnId", util.BitConversion.bitDecoder(ValidateTransactionSteps.validateTransactionPojo.txnRefNo))
                .replace("email", "")
                .replace("mobile", "")
                .replace("cancellationAmount", String.valueOf(ValidateTransactionSteps.validateTransactionPojo.amount.value))
                .toString();

        while(count <3) {
            loanCancellationPojo = loanCancellation.post(queryParamDetails, headerDetails, loanCancellationRequest);
            if (loanCancellationPojo.errorCode == null
                    || !loanCancellationPojo.errorCode.equalsIgnoreCase("CAN_REFUND_AFTER_WHILE"))
                break;
            count++;
        }

        Assert.assertEquals(loanCancellationPojo.status,"400", "Check if mobile number given is correct or service is down");
        Assert.assertEquals(loanCancellationPojo.message,"Something went wrong", "Check if mobile number given is correct or service is down");

    }



    @Description("To verify the loan is not cancelled for duplicate transaction with valid user")
    @Feature("LoanCancellationNegativeTestCases")
    @Test(priority = 6,groups = { "sanity", "regression"})
    public void verifyLoanCancellationForDuplicateTxn() throws Exception {
        int count = 0;
        LoanCancellationPojo loanCancellationPojo = new LoanCancellationPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();


        String loanCancellationRequest= new StringTemplate(UPIConstants.LOAN_CANCELLATION_REQUEST)
                .replace("saleTxnId", util.BitConversion.bitDecoder(ValidateTransactionSteps.validateTransactionPojo.txnRefNo))
                .replace("email", "")
                .replace("mobile", UPIData.REGISTERED_USER)
                .replace("cancellationAmount", String.valueOf(ValidateTransactionSteps.validateTransactionPojo.amount.value))
                .toString();

        while(count <3) {
            loanCancellationPojo = loanCancellation.post(queryParamDetails, headerDetails, loanCancellationRequest);
            if (loanCancellationPojo.errorCode == null
                    || !loanCancellationPojo.errorCode.equalsIgnoreCase("CAN_REFUND_AFTER_WHILE"))
                break;
            count++;
        }

        Assert.assertEquals(loanCancellationPojo.status,"400", "Check if the UPI transaction is not in loan cancelled state");
        Assert.assertTrue("duplicate cancel request".equals(loanCancellationPojo.message) ||
                "This loan is not confirmed yet.".equals(loanCancellationPojo.message) , "Check if the UPI transaction is not in loan cancelled state");
    }

}
