package upi.bnpl;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import upi.*;

public class LPUPITransactionalFlowTests {

    FindOrCreateMerchantSteps findOrCreateMerchantSteps = new FindOrCreateMerchantSteps();
    LPTransactionEligibilitySteps lpTransactionEligibilitySteps = new LPTransactionEligibilitySteps();
    PostIntentSteps postIntentSteps = new PostIntentSteps();
    InitiateTransactionSteps initiateTransactionSteps = new InitiateTransactionSteps();
    ValidateTransactionSteps validateTransactionSteps = new ValidateTransactionSteps();
    RefundUPISteps refundUPISteps = new RefundUPISteps();

    public LPUPITransactionalFlowTests() throws Exception {
    }

    @BeforeMethod
    public void LPUPITransactionalFlowTestsPrerequisites() {

    }

    @Description("To verify the LP UPI Transactional flow by using Scan&Pay method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 1, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingScanAndPay() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingScanAndPay();
        //Step 2 - User enters amount and system checks user's eligibility
        lpTransactionEligibilitySteps.verifyLPTransactionEligibilityUsingScanAndPay();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnScanAndPay();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPScanAndPay();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPScanAndPay();
    }

    @Description("To verify the LP UPI Refund API by using Scan&Pay method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 2, groups = {"UPILPRefund", "sanity", "regression"})
    public void verifyLPUPIRefundUsingScanAndPay() {

        //Step 6 - Refund the UPI BNPL Txn created in the previous test
        refundUPISteps.refundUPIScanAndPay();
    }
    
    @Description("To verify the LP UPI Transactional flow by using Entered VPA method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 3, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingEnteredVPA() throws Exception {

        //Step 1 - User manually enters merchant VPA and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingEnteredVPA();
        //Step 2 - User enters amount and system checks user's eligibility
        lpTransactionEligibilitySteps.verifyLPTransactionEligibilityUsingEnteredVPA();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnEnteredVPA();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPEnteredVPA();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPEnteredVPA();

    }

    @Description("To verify the LP UPI Refund API by using Entered VPA method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 4, groups = {"UPILPRefund", "sanity", "regression"})
    public void verifyLPUPIRefundUsingEnteredVPA() {

        //Step 6 - Refund the UPI BNPL Txn created in the previous test
        refundUPISteps.refundUPIEnteredVPA();
    }

    @Description("To verify the LP UPI Transactional flow by using Collect Request method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 5, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingCollectRequest() throws Exception {

        //Step 1 - User receives a collect request from a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingCollectRequest();
        //Step 2 - User enters amount and system checks user's eligibility
        lpTransactionEligibilitySteps.verifyLPTransactionEligibilityUsingCollectRequest();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnCollectRequest();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPCollectRequest();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPCollectRequest();

    }

    @Description("To verify the LP UPI Refund API by using Collect Request method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 6, groups = {"UPILPRefund", "sanity", "regression"})
    public void verifyLPUPIRefundUsingCollectRequest() {

        //Step 6 - Refund the UPI BNPL Txn created in the previous test
        refundUPISteps.refundUPICollectRequest();
    }

    @Description("To verify the LP UPI Transactional flow by using Intent Pay method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 7, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingIntentPay() throws Exception {

        //Step 1 - User intents to pay at a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingIntentPay();
        //Step 2 - User enters amount and system checks user's eligibility
        lpTransactionEligibilitySteps.verifyLPTransactionEligibilityUsingIntentPay();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnIntentPay();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPIntentPay();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPIntentPay();

    }

    @Description("To verify the LP UPI Refund API by using Intent Pay method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 8, groups = {"UPILPRefund", "sanity", "regression"})
    public void verifyLPUPIRefundUsingIntentPay() {

        //Step 6 - Refund the UPI BNPL Txn created in the previous test
        refundUPISteps.refundUPIIntentPay();
    }

    @Description("To verify the LP UPI Transactional flow by using Scan&Pay method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 9, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingScanAndPayDecline() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingScanAndPay();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnScanAndPay();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPScanAndPay();
        //Step 4 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPScanAndPayDecline();
    }

    @Description("To verify the LP UPI Transactional flow by using Entered VPA method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 10, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingEnteredVPADecline() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingEnteredVPA();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnEnteredVPA();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPEnteredVPA();
        //Step 4 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPEnteredVPADecline();
    }

    @Description("To verify the LP UPI Transactional flow by using Collect Request method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 11, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingCollectRequestDecline() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingCollectRequest();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnCollectRequest();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPCollectRequest();
        //Step 4 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPCollectRequestDecline();
    }

    @Description("To verify the LP UPI Transactional flow by using Intent Pay method")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 12, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPITransactionFlowUsingIntentPayDecline() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingIntentPay();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnIntentPay();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPIntentPay();
        //Step 4 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPIntentPayDecline();
    }

    @Description("To verify the LP UPI Refund API for an incorrect MTX")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 13, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPIRefundIncorrectMTX() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingIntentPay();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentLPTxnIntentPay();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionLPIntentPay();
        //Step 4 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionLPIntentPay();
        //Step 5 - Refund using incorrect MTX
        refundUPISteps.refundUPIIncorrectMTX();
    }

    @Description("To verify the LP UPI Refund API for an amount greater than sale amount")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 14, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPIRefundAmountGreaterThanSale() throws Exception {

        //Step 1 - Refund an amount greater than sale transaction's amount
        refundUPISteps.refundUPIGreaterThanSaleAmount();
    }

    @Description("To verify a partial LP UPI Refund")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 15, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPUPIPartialRefund() throws Exception {

        refundUPISteps.refundUPILPPartial();
    }

    @Description("To verify a pending partial LP UPI Refund on a transaction which is already refunded")
    @Feature("UPILPTransactionalHappyCases")
    @Test(priority = 16, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPIPendingPartialRefund() throws Exception {

        refundUPISteps.refundUPILPPartial();
    }

    @Description("To check the validity of the invalid merchant and make sure the transaction is not allowed")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 17, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyValidationOnInvalidMerchant() throws Exception {

       findOrCreateMerchantSteps.verifyFOCMerchantForInvalidMerchant();
    }

    @Description("To check mandatory validation on merchantVpa in the findOrCreateMerchant API")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 18, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnMerchantVPAForFOCMerchant() throws Exception {

        findOrCreateMerchantSteps.verifyMerchantVPAIsMandatoryForFOCMerchant();
    }

    @Description("To check the user is not eligible to make a transaction for an amount higher than their credit limit when using any method")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 19, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyUserIneligibleForHigherAmount() {

        lpTransactionEligibilitySteps.verifyUserIneligibleForHigherAmount();
    }

    @Description("To check the user is not eligible to make a transaction for an incorrect merchant VPA using any method")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 20, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPTransactionEligibilityForInvalidMerchant()  {

        lpTransactionEligibilitySteps.verifyLPTransactionEligibilityForInvalidMerchant();
    }

    @Description("To check the user is not eligible to make a transaction for an incorrect merchant VPA using any method")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 21, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyLPTransactionEligibilityForIncorrectMerchant()  {

        lpTransactionEligibilitySteps.verifyLPTransactionEligibilityForIncorrectMerchant();
    }

    @Description("To check mandatory validation on amount using any method")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 22, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnAmount()  {

        lpTransactionEligibilitySteps.verifyMandatoryValidationOnAmount();
    }

    @Description("To check mandatory validation on mobile using any method")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 23, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnMobile()  {

        lpTransactionEligibilitySteps.verifyMandatoryValidationOnMobile();
    }

    @Description("To check mandatory validation on mobile using any method")
    @Feature("UPILPTransactionalNegativeCases")
    @Test(priority = 24, groups = {"UPILPTransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnMerchantVPAForLPEligibility()  {

        lpTransactionEligibilitySteps.verifyMandatoryValidationOnMerchantVPAForLPEligibility();
    }
}
