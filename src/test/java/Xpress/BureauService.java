package Xpress;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import pojos.Xpress.PortfolioScrubMockResponsePojo;
import util.StringTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static Xpress.AmsService.fetchMuid;

@Slf4j
public class BureauService extends XpressData {
    public BureauService() throws Exception {
    }

    public static void mockCibilCbpFeatures(String approvalMethod, String mobile, String firstName, String lastName, String pan, String DOB) throws Exception {

        try {
            LocalDate currentDate = LocalDate.now().minusDays(1);
            LocalDate ExpDate = currentDate.plusDays(90);
            String creationDateEpoch = String.valueOf(currentDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
            String expirationDateEpoch = String.valueOf(ExpDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
            String[] parts = DOB.split("[-/]");
            String day = parts[0];
            String month = parts[1];
            String year = parts[2];
            // Remove leading zeros if present
            if (day.startsWith("0")) {
                day = day.substring(1);
            }
            if (month.startsWith("0")) {
                month = month.substring(1);
            }

            String cibilCbpResponse = cibilCbpMocking.postWithNoResponseBody(xpressGenericHeaders(), createCbpRequestPayload(approvalMethod, mobile, firstName, lastName, pan, year, month, day, creationDateEpoch, expirationDateEpoch));
            if (!cibilCbpResponse.equals("200")) {
                Assert.fail("Failed to Mock CBP due to invalid HTTP status");
            }
        } catch (Exception e) {
            log.error("Getting exception while mocking CibilCBP features with message" + e.getMessage());
        }
    }

    public static String createCbpRequestPayload(String approvalMethod, String mobile, String firstName, String lastName, String pan, String year, String month, String day, String creationDateEpoch, String expirationDateEpoch) {
        String CibilCbpBureauRequest = null;
        switch (approvalMethod) {
            case "BNPL_XSELL":
            case "BUREAU_SURROGATE":
            case "BNPL_XSELL_ONE_REPAYMENT":
            case "STATED_INCOME":
                CibilCbpBureauRequest = new StringTemplate(XpressData.CIBIL_CBP_MOCK_REQUEST)
                        .replace("panNumber", pan) //need to fetch from DB
                        .replace("phoneNum", mobile)
                        .replace("last_Name", lastName)
                        .replace("first_Name", firstName)
                        .replace("year", year)
                        .replace("day", day)
                        .replace("month", month)
                        .replace("riskScore", XpressData.BUREAU_SCORE)
                        .replace("creationDate", creationDateEpoch)
                        .replace("expirationDate", expirationDateEpoch)
                        .toString();
                break;
            case "BNPL_STPL":
                CibilCbpBureauRequest = new StringTemplate(XpressData.CIBIL_CBP_MOCK_REQUEST)
                        .replace("panNumber", pan) //need to fetch from DB
                        .replace("phoneNum", mobile)
                        .replace("last_Name", lastName)
                        .replace("first_Name", firstName)
                        .replace("year", year)
                        .replace("day", day)
                        .replace("month", month)
                        .replace("riskScore", XpressData.STPL_BUREAU_SCORE)
                        .replace("creationDate", creationDateEpoch)
                        .replace("expirationDate", expirationDateEpoch)
                        .toString();
            default:
                log.error("No valid approval method found");
        }
        return CibilCbpBureauRequest;
    }


    public static void mockHardPullBureauFeatures(String approvalMethod,String mobile, String firstName, String lastName, String pan, String DOB) throws Exception {

        try {

            String currentDate = LocalDate.now().minusDays(1).toString();
            String creationDate = convertDate(currentDate, "ddMMyyyy");
            ;
            String dateOfBirth1 = convertDate(DOB, "yyyy-MM-dd");
            String dateOfBirth2 = convertDate(DOB, "ddMMyyyy");
            String hardPullMockingResponse = cibilHardpullMocking.postWithNoResponseBody(xpressGenericHeaders(), createHardPullRequestPayload(approvalMethod,mobile, firstName, lastName, pan, dateOfBirth1, dateOfBirth2, creationDate));
            if (!hardPullMockingResponse.equals("200")) {
                Assert.fail("Failed to Mock HardPull due to invalid HTTP status");
            }
        } catch (Exception e) {
            log.error("Getting exception while mocking HardPull features with message" + e.getMessage());
        }
    }

    public static String createHardPullRequestPayload(String approvalMethod, String mobile, String firstName, String lastName, String pan, String dateOfBirth1, String dateOfBirth2, String creationDate) {
        String CibilHardPullBureauRequest = null;
        switch (approvalMethod) {
            case "BNPL_XSELL":
            case "BUREAU_SURROGATE":
            case "BNPL_XSELL_ONE_REPAYMENT":
            case "STATED_INCOME":
                CibilHardPullBureauRequest = new StringTemplate(XpressData.CIBIL_HARDPULL_MOCK_REQUEST)
                        .replace("pan", pan) //need to fetch from DB
                        .replace("mobile", mobile)
                        .replace("lastName", lastName)
                        .replace("firstName", firstName)
                        .replace("dateOfBirth1", dateOfBirth1)
                        .replace("dateOfBirth2", dateOfBirth2)
                        .replace("score", XpressData.BUREAU_SCORE)
                        .replace("dateProcessed", creationDate)
                        .toString();
                break;
            case "BNPL_STPL":
                CibilHardPullBureauRequest = new StringTemplate(XpressData.CIBIL_HARDPULL_MOCK_REQUEST)
                        .replace("pan", pan) //need to fetch from DB
                        .replace("mobile", mobile)
                        .replace("lastName", lastName)
                        .replace("firstName", firstName)
                        .replace("dateOfBirth1", dateOfBirth1)
                        .replace("dateOfBirth2", dateOfBirth2)
                        .replace("score", XpressData.STPL_BUREAU_SCORE)
                        .replace("dateProcessed", creationDate)
                        .toString();
                break;
            default:
                log.error("No valid approval method found");
        }
        return CibilHardPullBureauRequest;
    }

    public static void mockPortfolioScrubFeatures(String pan, String dob, String mobile) throws Exception {
        String userInputDob = dob;
        String newDob = dob.replaceAll("-", "");
        PortfolioScrubMockResponsePojo portfolioScrubMockResponsePojo = cibilPortfolioMocking.
                postWithoutQueryParams(xpressGenericHeaders(), createPortfolioScrubRequestPayload(pan, newDob, mobile));
        Assert.assertEquals(portfolioScrubMockResponsePojo.getMessage(), "Cibil Portfolio Scrub saved successfully", "Portfolio scrub mock api failure to invalid HTTP response.");
    }

    public static String createPortfolioScrubRequestPayload(String pan, String dob, String mobile) throws Exception {
        String PortfolioScrubRequest = null;
        String currentDate = LocalDate.now().toString();
        String formattedDate = convertDate(currentDate, "ddMMyyyy");
        PortfolioScrubRequest = new StringTemplate(XpressData.PORTFOLIO_SCRUB_MOCK_REQUEST)
                .replace("master_user_id", fetchMuid(mobile))
                .replace("pan", pan)
                .replace("ID_Number", pan)
                .replace("DateofBirth", dob)
                .replace("ScoreDate", formattedDate)
                .replace("DateProcessed", formattedDate)
                .toString();
        return PortfolioScrubRequest;
    }
}