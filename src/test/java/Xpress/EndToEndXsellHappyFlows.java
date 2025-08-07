package Xpress;

import dbUtils.KYC_MySQLSsh_DBAccessObject;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static Xpress.PaymentService.FetchMaxDPDOfLoan;
import static Xpress.PlatformService.fetchUserId;
import static Xpress.PscoreService.userDisbursalApi;
import static Xpress.ShylockService.*;

public class EndToEndXsellHappyFlows extends XpressData {


    public static String mobile = System.getProperty("mobile").trim();
    public static String firstName = System.getProperty("firstName");
    public static String lastName = System.getProperty("lastName");
    public static String panNumber = System.getProperty("pan");
    public static String dateOfBirth = System.getProperty("dateOfBirth").trim();
    public static String type = System.getProperty("type");
    public static String approvalMethod = System.getProperty("approvalMethod");
    public static String email = System.getProperty("email");
    public static String gender = System.getProperty("gender");
    public static String pincode = System.getProperty("pincode");
    public static String userId = null;
    public static String employmentStatus = System.getProperty("employmentStatus");
    public static String monthlySalary = System.getProperty("monthlySalary");
    Mocking mocking = new Mocking();

    public EndToEndXsellHappyFlows() throws Exception {
    }

    @BeforeTest(alwaysRun = true)
    public void BeforeClassWhitelisting() throws Exception {
        mocking.WhitelistingAndMockingUser();
    }

    @Test
    @Description("Approval method based end to end happy case Xsell Flows.")
    public void EndToEndXsellJourney() throws Exception {
        userId = fetchUserId(mobile);
        verifyLoanQualifierResponse(type, mobile, panNumber, true, true);
        matchNextStep("PAN", mobile, userId, type, panNumber, true, true);
        getPanFormDetails(userId, firstName, lastName, panNumber, gender, true, true);
        submitPanform(firstName, lastName, panNumber, gender, mobile, email, pincode, employmentStatus, monthlySalary, userId, dateOfBirth);
        matchNextStep("LOAN_PLAN_SELECTION", mobile, userId, type, panNumber, true, true);
        selectLoanPlan(userId, getLoanPlans(userId), type, mobile, panNumber, true, true);
        matchNextStep("COMMUNICATION_ADDRESS", mobile, userId, type, panNumber, true, true);
        submitCaForm(userId, approvalMethod, type, mobile, panNumber, true, true);
        matchNextStep("BANK_VERIFICATION", mobile, userId, type, panNumber, true, true);
        JourneyOverview(userId, approvalMethod);
        verifyBank(mobile);
        matchNextStep("SETUP_REPAYMENT", mobile, userId, type, panNumber, true, true);
        skipRepaymentStep(userId, type, mobile, panNumber, true, true);
        matchNextStep("UPLOAD_CONTACT_REFERENCES", mobile, userId, type, panNumber, true, true);
        submitContactReferences(userId);
        matchNextStep("APPLICATION_REVIEW", mobile, userId, type, panNumber, true, true);
        JourneyOverview(userId, approvalMethod);
        FetchSelectedLoanPlanDetails(userId);
        completeApplicationReviewStep(userId, type, mobile, panNumber, true, true);
        matchNextStep("KFS", mobile, userId, type, panNumber, true, true);
        getKfsData(userId);
        signKfs(userId);
        matchNextStep("SIGN_LOAN_AGREEMENT", mobile, userId, type, panNumber, true, true);
        signLoanAgreement(userId, mobile);
        deleteKycDataAfterApproval(mobile);
        userDisbursalApi(mobile);
    }

    @Test
    public void RepeatJourneyFlow() throws Exception {
        verifyLoanQualifierResponse(type, mobile, panNumber, true, true);
        matchNextStep("PAN", mobile, userId, type, panNumber, true, true);
        getPanFormDetails(userId, firstName, lastName, panNumber, gender, true, true);
        submitPanform(firstName, lastName, panNumber, gender, mobile, email, pincode, employmentStatus, monthlySalary, userId, dateOfBirth);
        matchNextStep("LOAN_PLAN_SELECTION", mobile, userId, type, panNumber, true, true);
        selectLoanPlan(userId, getLoanPlans(userId), type, mobile, panNumber, true, true);
        matchNextStep("COMMUNICATION_ADDRESS", mobile, userId, type, panNumber, true, true);
        submitCaForm(userId, approvalMethod, type, mobile, panNumber, true, true);
        matchNextStep("BANK_VERIFICATION", mobile, userId, type, panNumber, true, true);
        JourneyOverview(userId, approvalMethod);
        verifyBank(mobile);
        matchNextStep("SETUP_REPAYMENT", mobile, userId, type, panNumber, true, true);
        skipRepaymentStep(userId, type, mobile, panNumber, true, true);
        matchNextStep("UPLOAD_CONTACT_REFERENCES", mobile, userId, type, panNumber, true, true);
        submitContactReferences(userId);
        matchNextStep("APPLICATION_REVIEW", mobile, userId, type, panNumber, true, true);
        JourneyOverview(userId, approvalMethod);
        FetchSelectedLoanPlanDetails(userId);
        completeApplicationReviewStep(userId, type, mobile, panNumber, true, true);
        matchNextStep("KFS", mobile, userId, type, panNumber, true, true);
        getKfsData(userId);
        signKfs(userId);
        matchNextStep("SIGN_LOAN_AGREEMENT", mobile, userId, type, panNumber, true, true);
        signLoanAgreement(userId, mobile);
        deleteKycDataAfterApproval(mobile);
        userDisbursalApi(mobile);
        List<Integer> loanIds = FetchMaxDPDOfLoan(mobile);
        updateLoans(loanIds);
    }

    @AfterTest(alwaysRun = true)
    public void closeDbConnection() {
        try {
            KYC_MySQLSsh_DBAccessObject.closeDBConnection(KYC_MySQLSsh_DBAccessObject.getKycMysqlConnectionForXpress());
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while closing the DB connection " + e.getMessage());
        }
    }
}
