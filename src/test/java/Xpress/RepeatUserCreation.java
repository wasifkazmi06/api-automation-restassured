package Xpress;

import io.qameta.allure.Description;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static Xpress.AmsService.repeatAssessment;
import static Xpress.AmsService.scrubAssessment;
import static Xpress.PaymentService.FetchMaxDPDOfLoan;
import static Xpress.ShylockService.*;

public class RepeatUserCreation extends XpressData {

    public static String mobile = System.getProperty("mobile").trim();
    public static String panNumber = System.getProperty("pan");
    public static String type = System.getProperty("typeForRepeat");
    Mocking mocking = new Mocking();
    EndToEndXsellHappyFlows endToEndBnplXsellFlow = new EndToEndXsellHappyFlows();

    public RepeatUserCreation() throws Exception {
    }

    @BeforeTest(alwaysRun = true)
    public void BeforeRepeatUserCreation() throws Exception {
        mocking.WhitelistingAndMockingUser();
        endToEndBnplXsellFlow.EndToEndXsellJourney();
    }

    @Test
    @Description("Test for Repeat user whitelisting.")
    public void createRepeatUser() throws Exception {
        List<Integer> loanIds = FetchMaxDPDOfLoan(mobile);
        updateLoans(loanIds);
        verifyActiveStatusForFirstLoanUser(mobile, "PAN");
        scrubAssessment(mobile);
        repeatAssessment(panNumber);
        verifyLoanQualifierResponse(type, mobile, panNumber, true, false);
    }
}
