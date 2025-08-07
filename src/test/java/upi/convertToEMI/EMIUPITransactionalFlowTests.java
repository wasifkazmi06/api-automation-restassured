package upi.convertToEMI;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import upi.*;

public class EMIUPITransactionalFlowTests {

    FindOrCreateMerchantSteps findOrCreateMerchantSteps = new FindOrCreateMerchantSteps();
    EMITransactionEligibilitySteps emiTransactionEligibilitySteps = new EMITransactionEligibilitySteps();
    PostIntentSteps postIntentSteps = new PostIntentSteps();
    InitiateTransactionSteps initiateTransactionSteps = new InitiateTransactionSteps();
    ValidateTransactionSteps validateTransactionSteps = new ValidateTransactionSteps();
    RefundUPISteps refundUPISteps = new RefundUPISteps();

    public EMIUPITransactionalFlowTests() throws Exception {
    }

    @BeforeMethod
    public void LPUPITransactionalFlowTestsPrerequisites() {

    }

    @Description("To verify the EMI UPI Transactional flow by using Scan&Pay method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 1, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingScanAndPay() throws Exception {

        //Step 1 - User scans a valid QR code and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingScanAndPay();
        //Step 2 - User enters amount and system checks user's eligibility
        emiTransactionEligibilitySteps.verifyEMITransactionEligibilityUsingScanAndPay();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnScanAndPay();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMIScanAndPay();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionEMIScanAndPay();

    }

    @Description("To verify the EMI UPI Refund API by using Scan&Pay method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 2, groups = {"UPIEMIRefund", "sanity", "regression"})
    public void verifyEMIUPIRefundUsingScanAndPay() {

        //Step 6 - Refund the UPI EMI Txn created in the previous test
        refundUPISteps.refundUPIEMIScanAndPay();
    }


    @Description("To verify the EMI UPI Transactional flow by using Entered VPA method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 3, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingEnteredVPA() throws Exception {

        //Step 1 - User manually enters merchant VPA and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingEnteredVPA();
        //Step 2 - User enters amount and system checks user's eligibility
        emiTransactionEligibilitySteps.verifyEMITransactionEligibilityUsingEnteredVPA();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnEnteredVPA();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMIEnteredVPA();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionEMIEnteredVPA();

    }

    @Description("To verify the EMI UPI Refund API by using Entered VPA method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 4, groups = {"UPIEMIRefund", "sanity", "regression"})
    public void verifyEMIUPIRefundUsingEnteredVPA() {

        //Step 6 - Refund the UPI EMI Txn created in the previous test
        refundUPISteps.refundUPIEMIEnteredVPA();
    }

    @Description("To verify the EMI UPI Transactional flow by using Collect Request method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 5, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingCollectRequest() throws Exception {

        //Step 1 - User receives a collect request from a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingCollectRequest();
        //Step 2 - User enters amount and system checks user's eligibility
        emiTransactionEligibilitySteps.verifyEMITransactionEligibilityUsingCollectRequest();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnCollectRequest();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMICollectRequest();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionEMICollectRequest();

    }

    @Description("To verify the EMI UPI Refund API by using Collect Request method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 6, groups = {"UPIEMIRefund", "sanity", "regression"})
    public void verifyEMIUPIRefundUsingCollectRequest() {

        //Step 6 - Refund the UPI EMI Txn created in the previous test
        refundUPISteps.refundUPIEMICollectRequest();
    }

    @Description("To verify the EMI UPI Transactional flow by using Intent Pay method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 7, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingIntentPay() throws Exception {

        //Step 1 - User intents to pay at a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingIntentPay();
        //Step 2 - User enters amount and system checks user's eligibility
        emiTransactionEligibilitySteps.verifyEMITransactionEligibilityUsingIntentPay();
        //Step 3 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnIntentPay();
        //Step 4 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMIIntentPay();
        //Step 5 - User confirms the transactions
        validateTransactionSteps.verifyValidateTransactionEMIIntentPay();

    }

    @Description("To verify the EMI UPI Refund API by using Intent Pay method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 8, groups = {"UPIEMIRefund", "sanity", "regression"})
    public void verifyUPIEMIRefundUsingIntentPay() {

        //Step 6 - Refund the UPI EMI Txn created in the previous test
        refundUPISteps.refundUPIEMIIntentPay();
    }

    @Description("To verify the EMI UPI Transactional flow by using Scan&Pay method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 12, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingScanAndPayDecline() throws Exception {

        //Step 1 - User intents to pay at a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingScanAndPay();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnScanAndPay();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMIScanAndPay();
        //Step 4 - User declines the transactions
        validateTransactionSteps.verifyValidateTransactionEMIScanAndPayDecline();

    }

    @Description("To verify the EMI UPI Transactional flow by using Entered VPA method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 12, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingEnteredVPADecline() throws Exception {

        //Step 1 - User intents to pay at a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingEnteredVPA();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnEnteredVPA();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMIEnteredVPA();
        //Step 4 - User declines the transactions
        validateTransactionSteps.verifyValidateTransactionEMIEnteredVPADecline();

    }

    @Description("To verify the EMI UPI Transactional flow by using Collect Request method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 12, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingCollectRequestDecline() throws Exception {

        //Step 1 - User intents to pay at a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingCollectRequest();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnCollectRequest();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMICollectRequest();
        //Step 4 - User declines the transactions
        validateTransactionSteps.verifyValidateTransactionEMICollectRequestDecline();

    }

    @Description("To verify the LP UPI Transactional flow by using Intent Pay method")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 12, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPITransactionFlowUsingIntentPayDecline() throws Exception {

        //Step 1 - User intents to pay at a merchant and system checks merchant eligibility
        findOrCreateMerchantSteps.verifyFOCMerchantForValidMerchantUsingIntentPay();
        //Step 2 - UPI system creates a intent
        postIntentSteps.verifyPostIntentEMITxnIntentPay();
        //Step 3 - User initiates the transaction mapped to its intent ID
        initiateTransactionSteps.verifyInitiateTransactionEMIIntentPay();
        //Step 4 - User declines the transactions
        validateTransactionSteps.verifyValidateTransactionEMIIntentPayDecline();

    }

    @Description("To verify a partial EMI UPI Refund")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 13, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPIPartialRefund() throws Exception {

        //Step 1 - Convert a sale transaction into Loan
        verifyEMIUPITransactionFlowUsingScanAndPay();
        //Step 2 - Refund partial amount
        refundUPISteps.refundUPIEMIPartial();
    }

    @Description("To verify a pending partial EMI UPI Refund on a loan which is already refunded")
    @Feature("UPIEMITransactionalHappyCases")
    @Test(priority = 14, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyEMIUPIPendingPartialRefund() throws Exception {

        refundUPISteps.refundUPIEMIPartial();

    }

    @Description("To check the user is not eligible to make a transaction for an amount higher than their credit limit when using any method")
    @Feature("UPIEMITransactionalNegativeCases")
    @Test(priority = 15, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyUserIneligibleForHigherAmount() {

        emiTransactionEligibilitySteps.verifyUserIneligibleForHigherAmount();

    }

    @Description("To check the user is not eligible to make a transaction for an incorrect merchant VPA using any method")
    @Feature("UPIEMITransactionalNegativeCases")
    @Test(priority = 16, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyLPTransactionEligibilityForInvalidMerchant()  {

       emiTransactionEligibilitySteps.verifyEMITransactionEligibilityForInvalidMerchant();

    }

    @Description("To check the user is not eligible to make a transaction for an incorrect merchant VPA using any method")
    @Feature("UPIEMITransactionalNegativeCases")
    @Test(priority = 17, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyLPTransactionEligibilityForIncorrectMerchant()  {

       emiTransactionEligibilitySteps.verifyEMITransactionEligibilityForIncorrectMerchant();

    }

    @Description("To check mandatory validation on amount using any method")
    @Feature("UPIEMITransactionalNegativeCases")
    @Test(priority = 18, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnAmount()  {

       emiTransactionEligibilitySteps.verifyMandatoryValidationOnAmount();

    }

    @Description("To check mandatory validation on mobile using any method")
    @Feature("UPIEMITransactionalNegativeCases")
    @Test(priority = 19, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnMobile()  {

       emiTransactionEligibilitySteps.verifyMandatoryValidationOnMobile();

    }

    @Description("To check mandatory validation on mobile using any method")
    @Feature("UPIEMITransactionalNegativeCases")
    @Test(priority = 20, groups = {"UPIEMITransaction", "sanity", "regression"})
    public void verifyMandatoryValidationOnMerchantVPAForEMIEligibility()  {

       emiTransactionEligibilitySteps.verifyMandatoryValidationOnMerchantVPAForEMIEligibility();

    }

}
