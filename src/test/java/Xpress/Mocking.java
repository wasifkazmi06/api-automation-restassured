package Xpress;

import dbUtils.Pscore_DBAccessObject;
import dbUtils.Shylock_DBAccessObject;
import io.qameta.allure.Description;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static Xpress.AmsService.*;
import static Xpress.BureauService.*;
import static Xpress.OcrService.updateOcrData;
import static Xpress.PscoreService.creditExpireData;
import static Xpress.SecureService.fetchStatusAndUpdateBnplLimit;
import static Xpress.ShylockService.whitelistUserOnshylockDb;

@Slf4j
public class Mocking extends XpressData {

    public static String mobile = System.getProperty("mobile").trim();
    public static String firstName = System.getProperty("firstName");
    public static String lastName = System.getProperty("lastName");
    public static String panNumber = System.getProperty("pan");
    public static String dateOfBirth = System.getProperty("dateOfBirth").trim();
    public static String type = System.getProperty("type");
    public static String approvalMethod = System.getProperty("approvalMethod");

    public Mocking() throws Exception {

    }

    @SneakyThrows
    @Test
    @Description("Mocking and whitelisting user on xpress Test")
    public void WhitelistingAndMockingUser() {
        log.info("Test execution started for method ");
        creditExpireData(mobile);
        purgeUser(mobile, "sl");
        fetchStatusAndUpdateBnplLimit(mobile);
        if (type.equals("PRE_SELECTION")) {
            switch (approvalMethod) {
                case "BNPL_XSELL":
                case "STATED_INCOME":
                case "BNPL_STPL":
                case "BUREAU_SURROGATE":
                case "BNPL_XSELL_ONE_REPAYMENT":
                    mockHardPullBureauFeatures(approvalMethod, mobile, firstName, lastName, panNumber, dateOfBirth);
                    mockLazypayFeatures(approvalMethod, mobile);
                    mockOneRepaymentFeatures(mobile);
                    mockDarwinV5Features(mobile);
                    mockDarwinFeatures(approvalMethod, mobile);
                    mockCibilCbpFeatures(approvalMethod, mobile, firstName, lastName, panNumber, dateOfBirth);
                    updateOcrData(approvalMethod, mobile);
                    mockBscoreFeatures(mobile);
                    mockPortfolioScrubFeatures(panNumber, dateOfBirth, mobile);
                    whitelistUserOnshylockDb(firstName, lastName, mobile, panNumber, "", "", type);
                    break;
                default:
                    log.error("No valid approval method found");
            }
        }
    }

    @AfterTest(alwaysRun = true)
    public void closeDbConnection() {
        try {
            Pscore_DBAccessObject.closePostgreSqlDbConnection();
            Shylock_DBAccessObject.closeMysqlDbConnection();
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception while closing the DB connection " + e.getMessage());
        }
    }
}
