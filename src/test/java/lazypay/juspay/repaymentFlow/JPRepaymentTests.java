package lazypay.juspay.repaymentFlow;


import io.qameta.allure.Description;
import io.qameta.allure.Feature;

import lombok.SneakyThrows;
import mbe.authentication.DeviceConfigTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JPRepaymentTests {

    public JPRepaymentTests() throws Exception {
    }

    RepayDetailsSteps getRepaymentDetailsTests = new RepayDetailsSteps();

    SavedOptionSteps savedOptionsTests = new SavedOptionSteps();
    ValidateVPASteps validateVPATests = new ValidateVPASteps();
    GetPayBillViewSteps getPayBillViewSteps=new GetPayBillViewSteps();
    GetPaymentModesViewSteps getPaymentModesViewSteps=new GetPaymentModesViewSteps();
    InitiateRepayProcessSteps initiateRepayProcessSteps=new InitiateRepayProcessSteps();
    RepayRefundSteps repayRefundSteps=new RepayRefundSteps();
    RefundEnquirySteps refundEnquirySteps=new RefundEnquirySteps();

    CreateOrderSteps createOrderSteps=new CreateOrderSteps();
    public static String appVersion = "300"; // app version hard coded, to be changed when mbe implements on redis

    @Description("To get App version to login to app")
    @BeforeTest()
    public void beforeSuite() {

        //appVersion = DeviceConfigTest.fetchUpdatedAppVersion();
    }

    //Happy Flow
    //UPI
    @Description("To make an LP Happy Flow JPRepayment using UPI")
    @Feature("JPRepaymentE2E")
    @Test(groups = {"JPRepayments", "happyFlowJPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingUPI() throws Exception {

        getRepaymentDetailsTests.verifyRepayDetailsUPI();
        validateVPATests.verifyValidateVPAUPI();
//        savedOptionsTests.verifySavedOptionUPI();
        createOrderSteps.createOrderRepaymentUPI();
    }

    //Net banking
    @Description("To make an LP Happy Flow JPRepayment using NetBanking")
    @Feature("JPRepaymentE2E")
    @Test(groups = {"JPRepayments", "happyFlowJPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingNetBanking() throws Exception {
        getRepaymentDetailsTests.verifyRepayDetailsNetBanking();
        createOrderSteps.createOrderRepaymentNB();
    }

    //Debit Card
    @Description("To make an LP HappyFlow JPRepayment Using Debit Card")
    @Feature("JPRepaymentE2E")
    @Test(groups = {"JPRepayments", "happyFlowJPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingDebitCard() throws Exception {
        getRepaymentDetailsTests.verifyRepayDetailsDebitCard();
        //Need to create Test data such that Saved card will have that details
//        savedOptionsTests.verifySavedOptionDebitCard();
        createOrderSteps.createOrderRepaymentDC();
    }

    //Credit Card
    @Description("To make an LP repayment  Process Repayment")
    @Feature("JPRepaymentE2E")
    @Test(groups = {"JPRepayments", "happyFlowJPRepayment", "sanity", "regression"})
    public void verifyLPRepaymentUsingCreditCard() throws Exception {
        getRepaymentDetailsTests.verifyRepayDetailsCreditCard();
        //Need to create Test data such that Saved card will have that details
//        savedOptionsTests.verifySavedOptionCreditCard();
        createOrderSteps.createOrderRepaymentCC();
    }

    //Happy Flow
    //UPI
    @Description("To make an LP Happy Flow Repay Refund")
    @Feature("JPRepayRefund")
    @Test(groups = {"JPRepayments", "RepayRefund", "sanity", "regression"})
    public void verifyInvalidLPRepayRefund() throws Exception {

        getRepaymentDetailsTests.verifyRepayDetailsForRefund();
        createOrderSteps.createOrderRepaymentUPI();
        repayRefundSteps.verifyInValidRepayRefund();
        //refundEnquirySteps.refundEnquiryFortheUser();
    }
    /*//Happy Flow
    //UPI
    @Description("To make an LP Happy Flow Repay Refund")
    @Feature("JPRepayRefund")
    @Test(groups = {"JPRepayments", "RepayRefund", "sanity", "regression"})
    public void verifyLPRepayRefund() throws Exception {

        getRepaymentDetailsTests.verifyRepayDetailsForRefund();
        createOrderSteps.createOrderRepaymentUPI();
        repayRefundSteps.verifyRepayRefund();
        refundEnquirySteps.refundEnquiryFortheUser();
    }*/

    // Negative Test cases
    //Repay Details:
    @Description("To check invalid validation on mobile number for repayDetails API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"JPRepayments", "negativeJPRepayment", "sanity", "regression"})
    public void repayDetailsInvalidUser() throws Exception {

        getRepaymentDetailsTests.verifyRepayDetailsInvalidUser();


    }

    //Validate VPA
    @Description("To verify the validation on invalid VPA")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"JPRepayments", "negativeJPRepayment", "sanity", "regression"})
    public void verifyValidateVPAInvalidVPA() {

        validateVPATests.verifyValidateVPAInvalidVPA();
    }
    //Saved options
    @Description("To check invalid validation on mobile number for savedOptions API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"JPRepayments", "negativeLPRepayment", "sanity", "regression"})
    public void savedOptionsInvalidUser() throws Exception {

        savedOptionsTests.verifySavedOptionInvalidUser();

    }
    //Create -Order
    @Description("To check invalid validation on mobile number for createOrder API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"JPRepayments", "negativeLPRepayment", "sanity", "regression"})
    public void createOrderUserWithIncorrectAmount() throws Exception {

        createOrderSteps.createOrderRepaymentUPIInValidAmount();

    }

    //Create -Order
    @Description("To check invalid validation on mobile number for createOrder API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"JPRepayments", "negativeLPRepayment", "sanity", "regression"})
    public void createOrderUserWithAmountLessThan1() throws Exception {

        createOrderSteps.createOrderRepaymentUPIAmountLessThan1();

    }

    //Create -Order
    @Description("To check invalid validation on mobile number for savedOptions API")
    @Feature("LPRepaymentNegativeTestCases")
    @Test(groups = {"JPRepayments", "negativeLPRepayment", "sanity", "regression"})
    public void createOrderUserWithMissingField() throws Exception {

        createOrderSteps.createOrderRepaymentWithMissingField();

    }

//MBE -APP
    @Description("To make an LP Happy Flow JPRepayment using UPI")
    @Feature("JPRepaymentE2E_MBE")
    @Test(groups = {"JPRepayments", "happyFlow", "sanity", "regression"})
    public void verifyLPRepaymentViaAPPusingUPI() throws Exception {

        getPayBillViewSteps.verifyBillViewFortheUser(RepaymentData_JP.USER_MOBILE_UPI_MBE);
//        getPaymentModesViewSteps.verifyAllPaymentModesFortheUser(RepaymentData_JP.USER_MOBILE_UPI_MBE);
        initiateRepayProcessSteps.verifyRepaymentUsingUPI();


    }


    @Description("To make an LP Happy Flow JPRepayment using Debit card")
    @Feature("JPRepaymentE2E_MBE")
    @Test(groups = {"JPRepayments", "happyFlow", "sanity", "regression"})
    public void verifyLPRepaymentViaAPPusingDC() throws Exception {

        getPayBillViewSteps.verifyBillViewFortheUser(RepaymentData_JP.USER_MOBILE_DC_MBE);
//        getPaymentModesViewSteps.verifyAllPaymentModesFortheUser(RepaymentData_JP.USER_MOBILE_DC_MBE);
        initiateRepayProcessSteps.verifyRepaymentUsingDC();


    }

    @Description("To make an LP Happy Flow JPRepayment using Credit Card")
    @Feature("JPRepaymentE2E_MBE")
    @Test(groups = {"JPRepayments", "happyFlow", "sanity", "regression"})
    public void verifyLPRepaymentViaAPPusingCC() throws Exception {

        getPayBillViewSteps.verifyBillViewFortheUser(RepaymentData_JP.USER_MOBILE_CC_MBE);
//        getPaymentModesViewSteps.verifyAllPaymentModesFortheUser(RepaymentData_JP.USER_MOBILE_CC_MBE);
        initiateRepayProcessSteps.verifyRepaymentUsingCC();

    }
    @Description("To make an LP Happy Flow JPRepayment using NetBanking")
    @Feature("JPRepaymentE2E_MBE")
    @Test(groups = {"JPRepayments", "happyFlow", "sanity", "regression"})
    public void verifyLPRepaymentViaAPPusingNB() throws Exception {

        getPayBillViewSteps.verifyBillViewFortheUser(RepaymentData_JP.USER_MOBILE_NB_MBE);
//        getPaymentModesViewSteps.verifyAllPaymentModesFortheUser(RepaymentData_JP.USER_MOBILE_NB_MBE);
        initiateRepayProcessSteps.verifyRepaymentUsingNB();


    }

    @Description("To Check the repayment refund and enquiry ")
    @Feature("JPRepaymentE2E_MBE_Negative")
    @Test(groups = {"JPRepayments", "NegativeTestCases", "sanity", "regression"})
    public void verifyTheRepaymentWithInvalidOutStandingAmount() throws Exception {

        getPayBillViewSteps.verifyBillViewFortheUser(RepaymentData_JP.USER_MOBILE_MBE_NEGATIVE_INVALID_AMOUNT);
 //       getPaymentModesViewSteps.verifyAllPaymentModesFortheUser(RepaymentData_JP.USER_MOBILE_MBE_NEGATIVE_INVALID_AMOUNT);
        initiateRepayProcessSteps.verifyRepaymentUsingUPIWithIncorrectAmount();
    }

    @Description("To Check the repayment refund and enquiry ")
    @Feature("JPRepaymentE2E_MBE_Negative")
    @Test(groups = {"JPRepayments", "NegativeCases", "sanity", "regression"})
    public void verifyTheBillForInvalidUser() throws Exception {

        getPayBillViewSteps.verifyBillViewFortheInValidUser();


}



}

