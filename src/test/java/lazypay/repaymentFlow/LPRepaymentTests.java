package lazypay.repaymentFlow;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class LPRepaymentTests {
    public LPRepaymentTests() throws Exception {
    }

    RepayDetailsSteps getRepaymentDetailsTests = new RepayDetailsSteps();
    PreferredMethodSteps preferredMethodTests = new PreferredMethodSteps();
    SavedOptionsSteps savedOptionsTests = new SavedOptionsSteps();
    ValidateVPASteps validateVPATests = new ValidateVPASteps();
    SIAllowedSteps siAllowedTests = new SIAllowedSteps();
    BinHealthCheckSteps binHealthCheckTests = new BinHealthCheckSteps();
    InitiateRepaySteps initiateRepayTests = new InitiateRepaySteps();
    ProcessRepaySteps processRepayTests = new ProcessRepaySteps();
    SuccessRedirectSteps successRedirectTests = new SuccessRedirectSteps();

    @BeforeClass
    public void repaymentTestsPrerequisites() {


    }

    //Happy Flow
    //UPI
    @Description("To make an LP Happy Flow Repayment using UPI")
    @Feature("LPRepaymentE2E")
    @Test(groups = {"repayment", "happyFlowLPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingUPI() throws Exception {
        //Step 1 - Check repay details for the user and extract the total outstanding
        //log.info();
        getRepaymentDetailsTests.verifyRepayDetailsUPI();
        //Step 2 - Check that the preferred payment method set for the user is UPI
        preferredMethodTests.verifyPreferredMethodUPI();
        //Step 3 - Check that the saved repayment options for the user is UPI
        savedOptionsTests.verifySavedOptionUPI();
        /*Cant run validateVPA for a test VPA, will be tested independently
        //Step 4 - Validate the received VPA
        validateVPATests.verifyValidateVPAUPI();*/
        //Step 4 - Initiate the repayment using user's VPA
        initiateRepayTests.initiateRepaymentUPI();
        //Step 5 - Process the initiated repayment transaction
        processRepayTests.verifyProcessRepayUPI();
        //Step 6 - Direct user to success repayment page
        successRedirectTests.verifySuccessRedirectUPI();

    }

    //Credit Card
    @Description("To make an LP repayment  Process Repayment")
    @Feature("LPRepaymentE2E")
    @Test(groups = {"repayment", "happyFlowLPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingCreditCard() throws Exception {
        //Step 1 - Check repay details for the user and extract the total outstanding
        getRepaymentDetailsTests.verifyRepayDetailsCreditCard();
        //Step 2 - Check that the preferred payment method set for the user is CC
        preferredMethodTests.verifyPreferredMethodCreditCard();
        //Step 3 - Check that the saved repayment options for the user is CC
        savedOptionsTests.verifySavedOptionCreditCard();
        //Step 4 - Check that the saved card is not allowed for setting up a standing instruction
        siAllowedTests.verifyIfSIIsAllowedCreditCard();
        //Step 5 - Check the BIN health for the saved card
        binHealthCheckTests.verifyBinHealthCreditCard();
        //Step 6 - Initiate the repayment using saved credit card
        initiateRepayTests.initiateRepaymentCreditCard();
        //Step 7 - Process the initiated repayment transaction
        processRepayTests.verifyProcessRepayCreditCard();
        //Step 8 - Direct user to success repayment page
        successRedirectTests.verifySuccessRedirectCreditCard();
    }

    //Net banking
    @Description("To make an LP Happy Flow Repayment using NetBanking")
    @Feature("LPRepaymentE2E")
    @Test(groups = {"repayment", "happyFlowLPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingNetBanking() throws Exception {
        //Step 1 - Check repay details for the user and extract the total outstanding
        getRepaymentDetailsTests.verifyRepayDetailsNetBanking();
        //Step 2 - Check that the preferred payment method set for the user is Nb
        preferredMethodTests.verifyPreferredMethodNetBanking();
        //Step 3 - Initiate the repayment using user's NB
        initiateRepayTests.initiateRepaymentNetBanking();
        //Step 4 - Process the initiated repayment transaction
        processRepayTests.verifyProcessRepayNetBanking();
        //Step 5 - Direct user to success repayment page
        successRedirectTests.verifySuccessRedirectNetBanking();
    }

    //Debit Card
    @Description("To make an LP HappyFlow Repayment Using Debit Card")
    @Feature("LPRepaymentE2E")
    @Test(groups = {"repayment", "happyFlowLPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingDebitCard() throws Exception {
        //Step 1 - Check repay details for the user and extract the total outstanding
        getRepaymentDetailsTests.verifyRepayDetailsDebitCard();
        //Step 2 - Check that the preferred payment method set for the user is UPI
        preferredMethodTests.verifyPreferredMethodDebitCard();
        //Step 3 - Check that the saved repayment options for the user is UPI
        savedOptionsTests.verifySavedOptionDebitCard();
        //Step 4 - SI allowed for the Debit card
        siAllowedTests.verifyIfSIIsAllowedDebitCard();
        //Step 5 - BIN Health Check for the Debit card
        binHealthCheckTests.verifyBinHealthDebitCard();
        //Step 6 - Initiate the repayment using user's Debit card
        initiateRepayTests.initiateRepaymentDebitCard();
        //Step 7 - Process the initiated repayment transaction for Debit Card
        processRepayTests.verifyProcessRepayDebitCard();
        //Step 8 - Direct user to success repayment page
        successRedirectTests.verifySuccessRedirectDebitCard();
    }

    // Negative Test cases
    //Repay Details:
    @Description("To check invalid validation on mobile number for repayDetails API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void repayDetailsInvalidUser() throws Exception {

        getRepaymentDetailsTests.verifyRepayDetailsInvalidUser();


    }

    //Preferred Methods
    @Description("To check invalid validation on mobile number for preferredMethod API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void preferredMethodInvalidUser() throws Exception {

        preferredMethodTests.verifyPreferredMethodInvalidUser();


    }


    //Saved options
    @Description("To check invalid validation on mobile number for savedOptions API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void savedOptionsInvalidUser() throws Exception {

        savedOptionsTests.verifySavedOptionInvalidUser();


    }


    //Validate VPA
    @Description("To verify the validation on invalid VPA")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifyValidateVPAInvalidVPA() {

        validateVPATests.verifyValidateVPAInvalidVPA();


    }


    //SIAllowed
    @Description("To verify if the user is allowed to setup SI")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifyIfSIIsAllowedInvalidUser() {

        siAllowedTests.verifyIfSIIsAllowedInvalidUser();

    }


    //Bin Health Check
    @Description("To validate check on invalid BINs for a non CL user")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifyBinHealthInvalidBINCL0() {

        binHealthCheckTests.verifyBinHealthInvalidBINCL0();

    }

    @Description("To validate check on invalid BINs for a non CL")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifyBinHealthInvalidBINCL1() {

        binHealthCheckTests.verifyBinHealthInvalidBINCL1();

    }


    //Success Redirect
    @Description("To validate check on invalid transaction ID on SuccessRedirect API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifySuccessRedirectInvalidTxnId() {

        successRedirectTests.verifySuccessRedirectInvalidTxnId();

    }


    //Process Repay
    @Description("Negative Test for process repay when invalid Transaction id is given in body")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifyProcessRepayInvalidTransactionId()  {

        processRepayTests.verifyProcessRepayInvalidTransactionId();
    }

    @Description("Negative Test for process repay when TxnId, status, amount and pgId params are null")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void verifyProcessRepayNoDetailsInBody()  {

        processRepayTests.verifyProcessRepayNoDetailsInBody();
    }


    //Initiate Repay
    @Description("Negative Test for UPI repayment when UPI not Given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentWithoutUPI() {

        initiateRepayTests.initiateRepaymentWithoutUPI();
    }

    @Description("Negative Test for UPI repayment when Wrong Amount is given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void InitiateRepaymentUPIWithWrongAmount() {

        initiateRepayTests.InitiateRepaymentUPIWithWrongAmount();
    }

    @Description("Negative Test for Credit Card repayment when CC Details not Given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentWithoutCcDetails() {

        initiateRepayTests.initiateRepaymentWithoutCCDetails();
    }

    @Description("Negative Test for Credit Card repayment when Wrong Amount is given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentCCWithWrongAmount(){

        initiateRepayTests.initiateRepaymentCCWithWrongAmount();
    }

    @Description("Negative Test for Debit Card repayment when DC Details not Given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentWithoutDcDetails(){

        initiateRepayTests.initiateRepaymentWithoutDCDetails();
    }

    @Description("Negative Test for Debit Card repayment when Wrong Amount is given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentDcWithWrongAmount(){

        initiateRepayTests.initiateRepaymentDcWithWrongAmount();
    }

    @Description("Negative Test for Net Banking repayment when Wrong Amount is given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentNBWithWrongAmount(){

        initiateRepayTests.initiateRepaymentNBWithWrongAmount();
    }

    @Description("Negative Test for UPI repayment when PG/Bank Code not Given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentWithoutPgOrBankCodeDetails(){

        initiateRepayTests.initiateRepaymentWithoutPgOrBankCodeDetails();
    }

    @Description("Negative Test for Initiate Repayment when Amount is not Given")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"repayment", "negativeLPRepayment", "sanity", "regression"})
    public void initiateRepaymentWithoutAmount(){

        initiateRepayTests.initiateRepaymentWithoutAmount();

    }




    //individual positive Test cases

    //Validate Repay
    @Description("To validate check on invalid BINs for a non CL")
    @Feature("LPRepaymentPositveTestCases")
    @Test(groups = {"repayment", "sanity", "regression"})
    public void verifyValidateVPA() {

        validateVPATests.verifyValidateVPAUPI();

    }

    //Process Repay: Received Failed repayment
    @Description("To make an LP Happy Flow Repayment when repayment has failed using UPI")
    @Feature("LPRepaymentPositveTestCases")
    @Test(groups = {"repayment", "happyFlowLPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentFailedUsingUPI() throws Exception {
        //Step 1 - Check repay details for the user and extract the total outstanding
        getRepaymentDetailsTests.verifyRepayDetailsUPI();
        //Step 2 - Check that the preferred payment method set for the user is UPI
        preferredMethodTests.verifyPreferredMethodUPI();
        //Step 3 - Check that the saved repayment options for the user is UPI
        savedOptionsTests.verifySavedOptionUPI();
        //Step 4 - Initiate the repayment using user's VPA
        initiateRepayTests.initiateRepaymentUPI();
        //Step 5 - Process the initiated repayment transaction
        processRepayTests.verifyProcessRepayFailed();
    }
}
