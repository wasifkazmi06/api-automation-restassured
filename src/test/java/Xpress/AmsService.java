package Xpress;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import pojos.Xpress.AssessmentEngineMockResponsePojo;
import pojos.Xpress.RepeatAssessmentRequestPojo;
import pojos.Xpress.RepeatAssessmentResponsePojo;
import pojos.Xpress.ScrubAssessmentResponsePojo;
import util.StringTemplate;

import lombok.extern.slf4j.Slf4j;

import static Xpress.PscoreService.retrunPscoreGenericAssessmentResponse;
import static Xpress.PscoreService.userRegistrationOnPscore;

@Slf4j
public class AmsService extends XpressData {

    public AmsService() throws Exception {
    }

    static ObjectMapper objectMapper = new ObjectMapper();

    public static void mockLazypayFeatures(String approvalMethod, String mobile) throws Exception {
        AssessmentEngineMockResponsePojo lazypayResponsePojo = lazypayMocking.postWithoutQueryParams(xpressGenericHeaders(), createLazypayRequestPayload(approvalMethod, mobile));
        Assert.assertEquals(lazypayResponsePojo.message, "Data mocked successfully!!!", "lazypay mocking failed.");
    }

    public static String createLazypayRequestPayload(String approvalMethod, String mobile) throws Exception {
        String lazypayFeaturesRequest = null;
        switch (approvalMethod) {
            case "BNPL_XSELL":
            case "BNPL_STPL":
            case "STATED_INCOME":
            case "BUREAU_SURROGATE":
                lazypayFeaturesRequest = new StringTemplate(XpressData.LAZYPAY_FEATURES_MOCK_REQUEST)
                        .replace("phoneNumber", mobile)
                        .replace("rcmDecile", XpressData.LAZYPAY_RCM_DECILE)
                        .replace("maxDPDOnLP", XpressData.LAZYPAY_MAX_DPD)
                        .replace("RepaymentCycleOnLP", XpressData.LAZYPAY_CYCLE_ON_LP)
                        .toString();
                break;
            case "BNPL_XSELL_ONE_REPAYMENT":
                lazypayFeaturesRequest = new StringTemplate(XpressData.LAZYPAY_FEATURES_MOCK_REQUEST)
                        .replace("phoneNumber", mobile)
                        .replace("rcmDecile", XpressData.LAZYPAY_RCM_DECILE)
                        .replace("maxDPDOnLP", XpressData.LAZYPAY_MAX_DPD)
                        .replace("RepaymentCycleOnLP", XpressData.BNPL_XSELL_ONE_REPAYMENT_CYCLE_ON_LP)
                        .toString();
                break;
            default:
                log.error("No valid approval method found");
        }
        return lazypayFeaturesRequest;
    }

    public static void mockOneRepaymentFeatures(String mobile) throws Exception {
        String OneRepaymentFeaturesRequest = null;
        OneRepaymentFeaturesRequest = new StringTemplate(XpressData.ONE_REPAYMENT_FEATURES_MOCK_REQUEST)
                .replace("phoneNumber", mobile)
                .toString();
        AssessmentEngineMockResponsePojo OneRepaymentResponsePojo = OneRepaymentMocking.postWithoutQueryParams(xpressGenericHeaders(), OneRepaymentFeaturesRequest);
        Assert.assertEquals(OneRepaymentResponsePojo.message, "Data mocked successfully!!!", "OneRepayment mocking failed");
    }


    public static void mockBscoreFeatures(String mobile) throws Exception {
        String BscoreFeaturesRequest = null;
        BscoreFeaturesRequest = new StringTemplate(XpressData.BSCORE_FEATURES_MOCK_REQUEST)
                .replace("masterUserId", fetchMuid(mobile))
                .toString();
        AssessmentEngineMockResponsePojo BscoreResponsePojo = bscoreMocking.postWithoutQueryParams(xpressGenericHeaders(), BscoreFeaturesRequest);
        Assert.assertEquals(BscoreResponsePojo.message, "Data mocked successfully!!!", "Bscore mocking failed");
    }

    public static void mockDarwinFeatures(String approvalMethod, String mobile) throws Exception {
        AssessmentEngineMockResponsePojo darwinMockResponsePojo = darwinV6Mocking.
                postWithoutQueryParams(xpressGenericHeaders(), createDarwinRequestPayload(approvalMethod, mobile));
        Assert.assertEquals(darwinMockResponsePojo.message, "Data mocked successfully!!!", "Darwin mocking failed");
    }

    public static void mockDarwinV5Features(String mobile) throws Exception {
        String darwinV5FeaturesRequest = null;
        darwinV5FeaturesRequest = new StringTemplate(XpressData.DARWIN_V5_MOCK_REQUEST)
                .replace("masterUserId", fetchMuid(mobile))
                .toString();
        AssessmentEngineMockResponsePojo darwinV5ResponsePojo = darwinV5Mocking.postWithoutQueryParams(xpressGenericHeaders(), darwinV5FeaturesRequest);
        Assert.assertEquals(darwinV5ResponsePojo.message, "Data mocked successfully!!!", "DarwinV5 mocking failed");
    }

    public static String createDarwinRequestPayload(String approvalMethod, String mobile) throws Exception {
        String darwinFeaturesRequest = null;
        switch (approvalMethod) {
            case "BNPL_XSELL":
            case "BNPL_STPL":
            case "STATED_INCOME":
                darwinFeaturesRequest = new StringTemplate(XpressData.DARWIN_V6_MOCK_REQUEST)
                        .replace("masterUserId", fetchMuid(mobile))
                        .replace("bureau_pg_score", XpressData.DARWIN_BUREAU_PG_SCORE)
                        .replace("bureau_banking_pg_score", XpressData.DARWIN_BUREAU_BANKING_PG_SCORE)
                        .replace("bureau_sms_pg_score", XpressData.DARWIN_BUREAU_SMS_PG_SCORE)
                        .replace("bureau_banking_sms_pg_score", XpressData.DARWIN_BUREAU_BANKING_SMS_PG_SCORE)
                        .replace("has_bureau", XpressData.DARWIN_HAS_BUREAU)
                        .replace("has_pg", XpressData.DARWIN_HAS_PG)
                        .replace("has_banking", XpressData.DARWIN_HAS_BANKING)
                        .replace("has_sms", XpressData.DARWIN_HAS_SMS)
                        .toString();
                break;
            case "BUREAU_SURROGATE":
                darwinFeaturesRequest = new StringTemplate(XpressData.DARWIN_V6_MOCK_REQUEST)
                        .replace("masterUserId", fetchMuid(mobile))
                        .replace("bureau_pg_score", XpressData.BUREAU_SURROGATE_BUREAU_PG_SCORE)
                        .replace("bureau_banking_pg_score", XpressData.BUREAU_SURROGATE_BUREAU_BANKING_PG_SCORE)
                        .replace("bureau_sms_pg_score", XpressData.BUREAU_SURROGATE_BUREAU_SMS_PG_SCORE)
                        .replace("bureau_banking_sms_pg_score", XpressData.BUREAU_SURROGATE_BUREAU_BANKING_SMS_PG_SCORE)
                        .replace("has_bureau", XpressData.BUREAU_SURROGATE_HAS_BUREAU)
                        .replace("has_pg", XpressData.BUREAU_SURROGATE_HAS_PG)
                        .replace("has_banking", XpressData.BUREAU_SURROGATE_HAS_BANKING)
                        .replace("has_sms", XpressData.BUREAU_SURROGATE_HAS_SMS)
                        .toString();
                break;
            case "BNPL_XSELL_ONE_REPAYMENT":
                darwinFeaturesRequest = new StringTemplate(XpressData.DARWIN_V6_MOCK_REQUEST)
                        .replace("masterUserId", fetchMuid(mobile))
                        .replace("bureau_pg_score", XpressData.DARWIN_1REPAYMENT_BUREAU_PG_SCORE)
                        .replace("bureau_banking_pg_score", XpressData.DARWIN_BUREAU_BANKING_PG_SCORE)
                        .replace("bureau_sms_pg_score", XpressData.DARWIN_BUREAU_SMS_PG_SCORE)
                        .replace("bureau_banking_sms_pg_score", XpressData.DARWIN_BUREAU_BANKING_SMS_PG_SCORE)
                        .replace("has_bureau", XpressData.DARWIN_HAS_BUREAU)
                        .replace("has_pg", XpressData.DARWIN_HAS_PG)
                        .replace("has_banking", XpressData.DARWIN_HAS_BANKING)
                        .replace("has_sms", XpressData.DARWIN_HAS_SMS)
                        .toString();
                break;
            default:
                log.error("No valid approval method found");
        }
        return darwinFeaturesRequest;
    }

    public static String fetchMuid(String mobile) throws Exception {
        String muid = "";
        if (XpressData.getMaterUserId(mobile, "id").isEmpty()) {
            userRegistrationOnPscore(mobile);
            muid = XpressData.getMaterUserId(mobile, "id");
            return muid;
        } else {
            muid = XpressData.getMaterUserId(mobile, "id");
        }
        return muid;
    }

    public static void scrubAssessment(String mobile) throws Exception {
        Response scrubAssessmentResponse = scrubAssessment.
                postWithResponse(xpressAssessmentEngineHeaders(), retrunPscoreGenericAssessmentResponse(mobile));
        Assert.assertEquals(scrubAssessmentResponse.getStatusCode(), 200, "Scrub Assessment api failure due to invalid HTTP status code");
        ScrubAssessmentResponsePojo scrubAssessmentResponsePojo = scrubAssessmentResponse.as(ScrubAssessmentResponsePojo.class);
        Assert.assertEquals(scrubAssessmentResponsePojo.isSuccess, true, "Scrub assessment is failed.");
        Assert.assertEquals(scrubAssessmentResponsePojo.getBreType(), "XPRESS_SCRUB_POLICY", "Scrub assessment BRE type mismatch");
        Assert.assertNotNull(scrubAssessmentResponsePojo.getPolicyVersion(), "Policy version can not be null.");
        Assert.assertEquals(scrubAssessmentResponsePojo.getAssessmentKind(), "Inline", "Scrub assessment Assessment kind mismatch.");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getFirstLoanRuleResult().firstLoanApprovalMethodRuleDecision, "First loan approval method rule decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getOverallResult().payuFinScrubOverallResult, "Overall Scrub rule decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getOwnBooksResult().maxOverdueOnOwnBooksRuleDecision, "Own books max overdue decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getOwnBooksResult().maxSeasoningOnOwnBooksRuleDecision, "Own books max seasoning decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getOwnBooksResult().ownBookLatestEmiDPDRuleDecision, "Own books EMI DPD rule decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getOwnBooksResult().ownBookMaxDPDRuleDecision, "Own books max DPD rule decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getLazyPayResult().bnplLimitCapDecision, "Lazypay, BNPL limit cap decision is FALSE");
        Assert.assertTrue(scrubAssessmentResponsePojo.getResponse().getRaw().getLazyPayResult().payuFinRepeatRCMDecileDecision, "Lazypay repeat RCM decile decision is FALSE");
    }

    public static void repeatAssessment(String panNumber) throws Exception {
        RepeatAssessmentRequestPojo requestPojo = new RepeatAssessmentRequestPojo();
        requestPojo.setAssessment_lead_id(fetchAssessmentLeadId(panNumber));
        requestPojo = objectMapper.readValue(fetchRequestData(panNumber), RepeatAssessmentRequestPojo.class);
        Response repeatAssessmentResponse = repeatAssessment.postWithResponse(xpressArthamaticsHeaders(), objectMapper.writeValueAsString(requestPojo));
        Assert.assertEquals(repeatAssessmentResponse.getStatusCode(), 200, "Repeat Assessment api failure due to invalid HTTP status code");
        RepeatAssessmentResponsePojo repeatAssessmentResponsePojo = repeatAssessmentResponse.as(RepeatAssessmentResponsePojo.class);
        Assert.assertNotNull(repeatAssessmentResponsePojo.getData().getAssessmentType(), "AssessmentType should not be null.");
        Assert.assertNotNull(repeatAssessmentResponsePojo.getData().getAssessmentLeadId(), "AssessmentLead Id should not be null.");
        Assert.assertNotNull(repeatAssessmentResponsePojo.getData().getAssessmentRequestId(), "Assessment Request Id should not be null.");
        Assert.assertEquals(repeatAssessmentResponsePojo.getData().getAssessmentType(), ASSESSMENT_TYPE_REPEAT, "Assessment Type value mismatch.");
        Assert.assertNotNull(repeatAssessmentResponsePojo.getData().getProduct(), "Assessment product should not be null.");
        Assert.assertEquals(repeatAssessmentResponsePojo.getData().getProduct(), PRODUCT_OFFERING, "Repeat Assessment Product value mismatch.");
        Assert.assertEquals(repeatAssessmentResponsePojo.getData().getData().getXpressRepeatPolicy().isApproved, true, "Repeat assessment failed as it does not approved on any policy method.");
    }

}

