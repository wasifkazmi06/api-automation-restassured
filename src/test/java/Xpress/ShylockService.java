package Xpress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbUtils.Shylock_DBAccessObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import pojos.Xpress.*;
import pojos.platform.getUserData.UserData;
import util.StringTemplate;
import org.apache.commons.lang3.RandomStringUtils;

import static Xpress.PlatformService.*;
import static Xpress.PscoreService.agreementGetOtp;


@Slf4j
public class ShylockService extends XpressData {
    public ShylockService() throws Exception {
    }

    public static void whitelistUserOnshylockDb(String firstName, String lastName, String mobile, String panNumber,
                                                String assessmentleadId, String creditLimit, String type) throws Exception {
        String whitelistCsvFile = null;
        // header creation for shylock campaign user api
        HashMap<String, String> headerDetails = new HashMap<>();
        Map<String, Object> queryParamDetails = new HashMap<>();
        try {
            headerDetails.put("Content-Type", "multipart/form-data");

            // Delete entry from shylock pre-approved line table
            Shylock_DBAccessObject.deleteOnMySqlDb(DELETE_ENTRY_PRE_APPROVED.replace("$", mobile));

            if (type.equalsIgnoreCase("PRE_SELECTION")) {
                whitelistCsvFile = WHITELIST_CSV;
                XpressData.updateCSV(type, whitelistCsvFile, firstName + "" + lastName, panNumber, mobile,
                        "", "", "");
                queryParamDetails.put("type", type);
            }
            Response response = shylockApi.postWithFileUpload(queryParamDetails, headerDetails, whitelistCsvFile);
            Assert.assertEquals(response.statusCode(), 202, "Shylock File Upload Failed");

            // Validate if valid entry is being created in shylock preapproved line table
            Assert.assertTrue(checkIfValidWhitelistingDone(mobile), "Validation error occurred in shylock DB after whitelisting");

        } catch (Exception e) {
            Assert.assertTrue(false, "Getting exception while whitelisting user on shylock with" + e.getMessage());
        }
    }


    public static CashLoanOfferingResponsePojo verifyLoanQualifierResponse(String type, String mobile, String panNumber, boolean qualifiedToAvailLoans,
                                                                           boolean qualifiedByWhitelist) {

        String loanQualifierRequest = null;
        loanQualifierRequest = new StringTemplate(XpressData.CASHLOAN_OFFERING_REQUEST)
                .replace("mobile", mobile)
                .toString();

        Response cashLoanOfferingResponse = cashLoanOffering.putWithoutQueryParams
                (XpressData.xpressGenericHeaders(), loanQualifierRequest);
        Assert.assertEquals(cashLoanOfferingResponse.getStatusCode(), 200, "Cashloan offering api failure due to invalid HTTP status code");
        CashLoanOfferingResponsePojo cashLoanOfferingResponsePojo = cashLoanOfferingResponse.as(CashLoanOfferingResponsePojo.class);
        verifyLoanQualifier(type, cashLoanOfferingResponsePojo, mobile, panNumber, qualifiedToAvailLoans, qualifiedByWhitelist);
        return cashLoanOfferingResponsePojo;
    }

    public static void verifyLoanQualifier(String type, CashLoanOfferingResponsePojo loanQualifierResponse,
                                           String mobile, String panNumber, boolean qualifiedToAvailLoans,
                                           boolean qualifiedByWhitelist) {
        switch (type) {
            case "PRE_SELECTION":
                Assert.assertEquals(loanQualifierResponse.getQualifyingCriteria(), "PRE_SELECTION", "invalid Qualifying criteria.");
                Assert.assertEquals(loanQualifierResponse.qualifiedByWhitelist, qualifiedByWhitelist, "Invalid whitelisting.");
                break;
            case "PRE_APPROVAL":
            case "REPEAT":
                Assert.assertEquals(loanQualifierResponse.getQualifyingCriteria(), "REPEAT", "invalid Qualifying criteria.");
                Assert.assertEquals(loanQualifierResponse.qualifiedByWhitelist, qualifiedByWhitelist, "Invalid whitelisting type for repeat user");
                Assert.assertEquals(loanQualifierResponse.getQualifyingCriteria(), "PRE_SELECTION", "invalid Qualifying criteria");
                Assert.assertEquals(loanQualifierResponse.qualifiedByWhitelist, qualifiedByWhitelist, "Invalid whitelisting");
                break;
            case "NO_BNPL_ORGANIC":
            default:
                log.error("No valid type found");
                break;
        }
        Assert.assertEquals(loanQualifierResponse.getProductOffering(), PRODUCT_OFFERING, "Invalid product offering value in response.");
        Assert.assertEquals(loanQualifierResponse.qualifiedToAvailLoans, qualifiedToAvailLoans, "User not qualified to avail repeat loan.");
        Assert.assertEquals(loanQualifierResponse.getPhoneNumber(), mobile, "User input mobile number does not match number in response.");
        Assert.assertEquals(loanQualifierResponse.getPanNumber(), panNumber, "User input pan does not match with response Pan number.");
    }

    public static void getPanFormDetails(String userId, String firstName, String lastName, String panNumber, String gender,
                                         Boolean panVerified, Boolean kycSuccessful) {
        Response response = getPanDetails.getWithResponse(XpressData.xpressGenericHeaders(), userId);
        GetPanDetailsResponsePojo fetchPanDetailsResponse = response.as(GetPanDetailsResponsePojo.class);
        if (response.getStatusCode() != 200) {
            Assert.fail("Get pan details api failure due to invalid HTTP status code");
        }
        Assert.assertNotNull(fetchPanDetailsResponse.getPanNumber(), "Get pan details api failure because KYC is not completed for the user");
    }

    //TO-DO
//    public static void verifyPanFormDetails(String firstName, String lastName, String panNumber, String gender,
//                                            GetPanDetailsResponsePojo fetchPanDetailsResponse ){
//        if (fetchPanDetailsResponse.getPanNumber()!=null){
//            Assert.assertEquals(fetchPanDetailsResponse.getPanNumber(),panNumber, "User input pan and response pan number mismatch");
//        }
//        if (fetchPanDetailsResponse.getFirstName()!=null){
//            Assert.assertEquals(fetchPanDetailsResponse.getFirstName(),firstName, "User input firstName and response firstName mismatch");
//        }
//        if (fetchPanDetailsResponse.getLastName()!=null){
//            Assert.assertEquals(fetchPanDetailsResponse.getLastName(),lastName, "User input lastName and lastName number mismatch");
//        }
//        if (fetchPanDetailsResponse.getGender()!=null){
//            Assert.assertEquals(fetchPanDetailsResponse.getGender(),gender, "User input gender and response gender mismatch");
//        }
//        if (fetchPanDetailsResponse.getProductOffering()!=null){
//            Assert.assertEquals(fetchPanDetailsResponse.getProductOffering(),PRODUCT_OFFERING, "Incorrect product offering value in response");
//        }
//    }

    public static GetPanDetailsResponsePojo returnPanFormData(String userId) {
        Response response = getPanDetails.getWithResponse(XpressData.xpressGenericHeaders(), userId);
        GetPanDetailsResponsePojo fetchPanDetailsResponse = response.as(GetPanDetailsResponsePojo.class);
        if (response.getStatusCode() != 200) {
            Assert.fail("Get pan details api failure due to invalid HTTP status code");
        }
        return fetchPanDetailsResponse;
    }

    public static void submitPanform(String firstName, String lastName, String panNumber, String gender, String mobile,
                                     String email, String pincode, String employmentStatus, String monthlySalary, String userId, String dob) throws JsonProcessingException {
        GetPanDetailsResponsePojo responsePojo = returnPanFormData(userId);
        String payload = prepareSubmitPanRequest(firstName, lastName, panNumber, gender, mobile, email, pincode, employmentStatus, monthlySalary, responsePojo, dob);
        SubmitPanFormResponsePojo submitPanFormResponsePojo = submitPanForm.postWithoutQueryParams(XpressData.xpressGenericHeaders(), payload);
        Assert.assertEquals(submitPanFormResponsePojo.getProductOffering(), PRODUCT_OFFERING, "Getting error while Submit pan form request");
        Assert.assertEquals(submitPanFormResponsePojo.getMessage(), SUCCESS_PANFORM_SUBMISSION_MESSAGE, "Incorrect message after submitting pan form data.");
        Assert.assertNotNull(submitPanFormResponsePojo.getUmUUID(), "umUUID cannot be empty or null");
        Assert.assertEquals(submitPanFormResponsePojo.getLastCompletedCheckpoint(), "INITIATED", "Incorrect last completed checkpoint after submitting pan form data.");
    }

    public static String prepareSubmitPanRequest(String firstName, String lastName, String panNumber, String gender, String mobile,
                                                 String email, String pincode, String employmentStatus, String monthlySalary, GetPanDetailsResponsePojo responsePojo, String dob) throws JsonProcessingException {
        SubmitPanFormRequestPojo requestPojo = new SubmitPanFormRequestPojo();
        ObjectMapper obj = new ObjectMapper();
        String panFormRequestPayload = null;
        if (responsePojo.isKycSuccessful()) {
            requestPojo.setFirstName(responsePojo.getFirstName());
            requestPojo.setLastName(responsePojo.getLastName());
            requestPojo.setDob(responsePojo.getDob());
            requestPojo.setGender(responsePojo.getGender());
            requestPojo.setPanNumber(responsePojo.getPanNumber());
            requestPojo.setCurrentPinCode(responsePojo.getPinCode());
            requestPojo.setEmploymentStatus(responsePojo.getEmploymentStatus());
        } else {
            requestPojo.setFirstName(firstName);
            requestPojo.setLastName(lastName);
            requestPojo.setDob(dob);
            requestPojo.setGender(gender);
            requestPojo.setPanNumber(panNumber);
            requestPojo.setCurrentPinCode(pincode);
            requestPojo.setEmploymentStatus(employmentStatus);
        }
        requestPojo.setMobile(mobile);
        requestPojo.setEmail(email);
        requestPojo.setMonthlySalary(monthlySalary);
        requestPojo.setSource(SOURCE_OF_SUBMIT_PANFORM);
        requestPojo.setReasonForLoan(REASON_FOR_LOAN);
        requestPojo.setBureauAndCkycConsent(BUREAU_AND_CKYC_CONSENT_VALUE);
        requestPojo.setNonMicroFinanceConsent(NONMICRO_FINANCE_CONSENT_VALUE);
        panFormRequestPayload = obj.writeValueAsString(requestPojo);
        return panFormRequestPayload;
    }

    public static void matchNextStep(String nextStepToCheck, String mobile, String userId, String type, String panNumber, boolean qualifiedToAvailLoans,
                                     boolean qualifiedByWhitelist) throws InterruptedException {
        CashLoanOfferingResponsePojo loanOfferingResponsePojo = verifyLoanQualifierResponse(type, mobile, panNumber, qualifiedToAvailLoans, qualifiedByWhitelist);
        int counter = 9;
        String nextStep = null;
        String updatedNextStep = null;
        //Thread.sleep(10000);
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("phoneNumber", mobile);
        Response onboardingResponse = onboardingCaseStatus.getWithResponse(queryParams, XpressData.xpressGenericHeaders());
        Assert.assertEquals(onboardingResponse.getStatusCode(), 200, "Onboarding case status api failure due to invalid HTTP status code.");
        OnboardingCaseStatusResponsePojo responsePojo = onboardingResponse.as(OnboardingCaseStatusResponsePojo.class);
        nextStep = responsePojo.nextStep;
        Assert.assertEquals(loanOfferingResponsePojo.getProductOffering(), responsePojo.getProductOffering(), "Product offering mismatch in cashloan offering and onboarding api");

        if (!nextStep.equalsIgnoreCase(nextStepToCheck)) {
            switch (nextStep) {
                case "WAIT":
                    for (int i = 1; i <= counter; i++) {
                        if (nextStepToCheck.equalsIgnoreCase(nextStep)) {
                            return; // need to check this break will not break the switch
                        }
                        Thread.sleep(10000);
                        Response onboardingResponseStatusCode = onboardingCaseStatus.getWithResponse(queryParams, XpressData.xpressGenericHeaders());
                        Assert.assertEquals(onboardingResponseStatusCode.getStatusCode(), 200, "Onboarding case status api failure due to invalid HTTP status code.");
                        OnboardingCaseStatusResponsePojo responseStatusPojo = onboardingResponseStatusCode.as(OnboardingCaseStatusResponsePojo.class);
                        updatedNextStep = responseStatusPojo.nextStep;
                        nextStep = updatedNextStep;
                    }
                    Assert.fail("Expected: " + nextStepToCheck + " and Actual: " + nextStep + " next step does not match, WAIT encountered.");
                    break;
                case "OVERVIEW":
                    if (nextStepToCheck.equalsIgnoreCase(responsePojo.nextJourneyStep)) {
                        break;
                    }
                    Assert.fail("Expected: " + nextStepToCheck + " and Actual: " + responsePojo.nextJourneyStep + " next step does not match, OVERVIEW encountered");
                default:
                    Assert.fail("Unknown next step in the journey");
                    break;
            }
        } else {
            Assert.assertEquals(nextStepToCheck, responsePojo.nextStep, "Actual and expected next step matched.");
        }
    }


    public static String returnNextStep(String mobile) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("phoneNumber", mobile);
        Response onboardingResponse = onboardingCaseStatus.getWithResponse(queryParams, XpressData.xpressGenericHeaders());

        Assert.assertEquals(onboardingResponse.getStatusCode(), 200, "Onboarding case status api failure.");
        OnboardingCaseStatusResponsePojo responsePojo = onboardingResponse.as(OnboardingCaseStatusResponsePojo.class);
        return responsePojo.getNextStep();
    }

    public static GetLoanPlansResponsePojo getLoanPlans(String userId) {
        Response getPlansResponse = getLoanPlans.getWithResponse(XpressData.xpressGenericHeaders(), userId);
        Assert.assertEquals(getPlansResponse.getStatusCode(), 200, "Get plans api failure due to invalid HTTP status code");
        GetLoanPlansResponsePojo getPlanDetailsResponsePojo = getPlansResponse.as(GetLoanPlansResponsePojo.class);

        if (getPlanDetailsResponsePojo.getMinAmount() <= 0.0 || getPlanDetailsResponsePojo.getMaxAmount() <= 0.0 ||
                getPlanDetailsResponsePojo.getPlans().size() <= 0) {
            Assert.fail("Incorrect plan data in response");
        }
        return getPlanDetailsResponsePojo;
    }

    public static void selectLoanPlan(String userId, GetLoanPlansResponsePojo getLoanPlansResponsePojo, String type, String mobile, String panNumber,
                                      boolean qualifiedToAvailLoans,
                                      boolean qualifiedByWhitelist) {
        CashLoanOfferingResponsePojo loanOfferingResponsePojo = verifyLoanQualifierResponse(type, mobile, panNumber, qualifiedToAvailLoans,
                qualifiedByWhitelist);
        Response selectLoanPlanResponse = selectLoanPlan.postWithResponseBody(userId, XpressData.xpressGenericHeaders(),
                prepareSelectLoanPlanRequest(getLoanPlansResponsePojo));
        OnboardingCaseStatusResponsePojo selectLoanPlanResponsePojo = selectLoanPlanResponse.as(OnboardingCaseStatusResponsePojo.class);

        Assert.assertEquals(selectLoanPlanResponse.getStatusCode(), 200, "Select loan plan api failure due to invalid HTTP status code");
        Assert.assertEquals(selectLoanPlanResponsePojo.getNextStep(), NEXT_STEP_AFTERLOANPLAN_SELECT, "Incorrect next step after selecting plan from loan step");
        Assert.assertEquals(selectLoanPlanResponsePojo.getTotalCreditLimit(), getLoanPlansResponsePojo.getMaxCreditLine(), "Maximum credit limit mismatch for user");
        Assert.assertEquals(selectLoanPlanResponsePojo.getLastCompletedCheckpoint(), LAST_COVERED_CHECKPOINT_AFTER_SELECTLOANPLAN, "Incorrect last covered checkpoint selecting plan from loan step");
        Assert.assertEquals(selectLoanPlanResponsePojo.getLastCoveredApplicableCheckpoint(), LAST_COVERED_APPLICABLE_CHECKPOINT_AFTER_SELECTLOANPLAN, "Incorrect last coverable applicable checkpoint after selecting plan from loan step");
        Assert.assertEquals(loanOfferingResponsePojo.getProductOffering(), selectLoanPlanResponsePojo.getProductOffering(), "Product offering mismatch");
    }

    public static String prepareSelectLoanPlanRequest(GetLoanPlansResponsePojo getLoanPlansResponsePojo) {
        String selectLoanPlanRequest = null;
        selectLoanPlanRequest = new StringTemplate(XpressData.SELECT_LOAN_PLANS_REQUEST).replace("plan", String.valueOf(getLoanPlansResponsePojo.getPlans().get(0).getPlanId()))
                .replace("amount", String.valueOf(getLoanPlansResponsePojo.getPlans().get(0).getMinAmount()))
                .toString();
        return selectLoanPlanRequest;
    }

    public static void submitCaForm(String userId, String approvalMethod, String type, String mobile, String panNumber,
                                    boolean qualifiedToAvailLoans,
                                    boolean qualifiedByWhitelist) throws Exception {
        CashLoanOfferingResponsePojo loanOfferingResponsePojo = verifyLoanQualifierResponse(type, mobile, panNumber, qualifiedToAvailLoans,
                qualifiedByWhitelist);

        Response submitCaFormResponse = submitCaForm.postWithResponseBody(userId, XpressData.xpressGenericHeaders(), prepareCaFormRequestBody(mobile));
        Assert.assertEquals(submitCaFormResponse.getStatusCode(), 200, "Submit CA form api failure due to invalid HTTP status code");
        OnboardingCaseStatusResponsePojo caFormResponsePojo = submitCaFormResponse.as(OnboardingCaseStatusResponsePojo.class);
        // Assert.assertEquals(caFormResponsePojo.getLastCompletedCheckpoint(), LAST_COVERED_CHECKPOINT_AFTER_CA_FORM, "Incorrect last covered checkpoint after completing CA form step");
        Assert.assertEquals(caFormResponsePojo.getLastCoveredApplicableCheckpoint(), LAST_COVERED_APPLICABLE_CHECKPOINT_AFTER_CA_FORM, "Incorrect last coverable applicable checkpoint after completing CA form step");
        Assert.assertEquals(loanOfferingResponsePojo.getProductOffering(), caFormResponsePojo.getProductOffering(), "Product offering mismatch");


        if (approvalMethod.equalsIgnoreCase("BNPL_XSELL") || approvalMethod.equalsIgnoreCase("BUREAU_SURROGATE") ||
                approvalMethod.equalsIgnoreCase("BNPL_STPL") || approvalMethod.equalsIgnoreCase("BNPL_XSELL_ONE_REPAYMENT")) {
            Assert.assertEquals(caFormResponsePojo.getNextStep(), "OVERVIEW", "Submit CA form api failure due to invalid HTTP status code");
        } else {
            Assert.assertEquals(caFormResponsePojo.getNextStep(), "EMPLOYMENT_INFO", "Submit CA form api failure due to invalid HTTP status code");
        }
    }

    public static String prepareCaFormRequestBody(String mobile) throws Exception {

        SubmitCaFormRequestPojo requestPojo = new SubmitCaFormRequestPojo();
        ObjectMapper obj = new ObjectMapper();
        String submitCaFormRequest = null;
        String pincode = null;
        String name = null;
        String district = null;
        String state = null;
        UserData data = returnUserData(mobile);
        pincode = data.getUserAddress().get(0).getZipCode();
        String addressData = fetchAddressByPincode(pincode);
        JSONArray jsonArray = new JSONArray(addressData);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        if ("Success".equals(jsonObject.getString("Status"))) {
            JSONArray postOffices = jsonObject.getJSONArray("PostOffice");
            JSONObject pinAddress = postOffices.getJSONObject(0);
            name = pinAddress.getString("Name");
            district = pinAddress.getString("District");
            state = pinAddress.getString("State");
        } else {
            System.out.println("No data found for the provided pincode.");
        }
        requestPojo.setCity(district);
        requestPojo.setHouseNo("1");
        requestPojo.setLandmark(district);
        requestPojo.setPinCode(pincode);
        requestPojo.setResidenceType("Owned");
        requestPojo.setState(state);
        requestPojo.setStreet(name);
        submitCaFormRequest = obj.writeValueAsString(requestPojo);
        return submitCaFormRequest;
    }

    public static void JourneyOverview(String userId, String approvalMethod) {
        Response journeyOverviewResponse = journeyOverview.getWithResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(journeyOverviewResponse.getStatusCode(), 200, "Overview api failure due to invalid HTTP status code");
        JourneyOverviewResponsePojo responsePojo = journeyOverviewResponse.as(JourneyOverviewResponsePojo.class);
        verifyJourneyOverviewResponse(responsePojo);
    }

    public static void verifyJourneyOverviewResponse(JourneyOverviewResponsePojo responsePojo) {
        Assert.assertTrue(responsePojo.getJourneyCheckpointGroups().size() > 0, "Empty journey overview list is coming in response");
        Assert.assertEquals(responsePojo.getJourneyCheckpointGroups().get(0).getGroupName(), "Basic details", "Group name on first index mismatch");
        Assert.assertEquals(responsePojo.getJourneyCheckpointGroups().get(1).getGroupName(), "Prepare for loan disbursal", "Group name on second index mismatch");
        Assert.assertEquals(responsePojo.getJourneyCheckpointGroups().get(2).getGroupName(), "Sign your Agreement", "Group name on first third mismatch");
        Assert.assertTrue(responsePojo.getJourneyCheckpointGroups().get(0).getJourneyCheckpointSubGroups().size() > 0, "Basic details checkpoint list is empty");
        Assert.assertTrue(responsePojo.getJourneyCheckpointGroups().get(1).getJourneyCheckpointSubGroups().size() > 0, "Prepare for loan disbursal checkpoint list is empty");
    }

    public static void verifyBank(String mobile) {
        String verifyBankRequest = new StringTemplate(XpressData.BANK_VERIFY_REQUEST).replace("umuuid", returnUmuuid(mobile)).replace("accountNo", RandomStringUtils.randomNumeric(12))
                .toString();
        Response bankVerifyResponse = verifyBankAccount.postWithResponse(verifyBankRequest, XpressData.xpressGenericHeaders());
        Assert.assertEquals(bankVerifyResponse.getStatusCode(), 200, "Bank verify api failure due to invalid HTTP status code");
        BankVerifyResponsePojo bankVerifyResponsePojo = bankVerifyResponse.as(BankVerifyResponsePojo.class);
        Assert.assertTrue(bankVerifyResponsePojo.isValidated(), "Bank verification is not successful");
        Assert.assertNotNull(bankVerifyResponsePojo.getRefKey(), "Bank verification is not successful as ref key is empty in response");
    }

    //Skip repayment and validate product offering
    public static void skipRepaymentStep(String userId, String type, String mobile, String panNumber, boolean qualifiedToAvailLoans,
                                         boolean qualifiedByWhitelist) {
        CashLoanOfferingResponsePojo loanOfferingResponsePojo = verifyLoanQualifierResponse(type, mobile, panNumber, qualifiedToAvailLoans,
                qualifiedByWhitelist);
        OnboardingCaseStatusResponsePojo skipRepaymentResponse = skipRepayment.putWithPathParams(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(skipRepaymentResponse.getLastCompletedCheckpoint(), LAST_COVERED_CHECKPOINT_AFTER_SKIP_REPAYMENT, "Incorrect last covered checkpoint after skip repayment step");
        Assert.assertEquals(skipRepaymentResponse.getLastCoveredApplicableCheckpoint(), LAST_COVERED_APPLICABLE_CHECKPOINT_AFTER_SKIP_REPAYMENT, "Incorrect last covered applicable checkpoint after skip repayment step");
        Assert.assertEquals(loanOfferingResponsePojo.getProductOffering(), skipRepaymentResponse.getProductOffering(), "Product offering mismatch");
    }

    // Method to return references and required reference count
    public static Map<String, Integer> returnReferenceRequiredCount(String userId) {
        Response response = checkReferenceRequiredCount.getWithResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(response.getStatusCode(), 200, "Reference required count API failure due to invalid HTTP status code");
        CheckRefRequiredCountResponsePojo responsePojo = response.as(CheckRefRequiredCountResponsePojo.class);
        Map<String, Integer> result = new HashMap<>();
        result.put("references", responsePojo.getReferences().size());
        result.put("requiredReferenceCount", responsePojo.getRequiredReferenceCount());
        return result;
    }

    public static void FetchSelectedLoanPlanDetails(String userId) {
        Response selectedPlanDetails = selectedLoanPlanDetails.getWithResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(selectedPlanDetails.getStatusCode(), 200, "Selected loan plan details api failure due to invalid HTTP status code");
        SelectedLoanPlanDetailsResponsePojo selectedLoanPlanDetailsResponsePojo = selectedPlanDetails.as(SelectedLoanPlanDetailsResponsePojo.class);
        verifySelectedLoanDetailsData(selectedLoanPlanDetailsResponsePojo);
    }

    public static void verifySelectedLoanDetailsData(SelectedLoanPlanDetailsResponsePojo selectedLoanPlanDetailsResponsePojo) {
        Assert.assertNotNull(selectedLoanPlanDetailsResponsePojo.getLoanApplicationId(), "Mandatory loan application id field can not be null");
        Assert.assertNotNull(selectedLoanPlanDetailsResponsePojo.getStatus(), "Mandatory status field can not be null");
        Assert.assertNotNull(selectedLoanPlanDetailsResponsePojo.getLoan(), "Mandatory Loan object can not be null");
        Assert.assertNotNull(selectedLoanPlanDetailsResponsePojo.getProcessingFee(), "Processing fees data object can not be null");
        Assert.assertNotNull(selectedLoanPlanDetailsResponsePojo.getInsuranceDetails(), " Insurance data field object can not be null");
        Assert.assertTrue(selectedLoanPlanDetailsResponsePojo.isLoanPlanSelected(), "Incorrect status of loan plan selected  field");
        Assert.assertTrue(selectedLoanPlanDetailsResponsePojo.isApplicationSubmitted(), "Incorrect status of application submitted  field");
        Assert.assertFalse(selectedLoanPlanDetailsResponsePojo.isApplicationApproved(), "Incorrect status of application approved  field");
        Assert.assertTrue(selectedLoanPlanDetailsResponsePojo.isApplicationPreApproved(), "Incorrect status of application pre-approved  field");
        if (selectedLoanPlanDetailsResponsePojo.getLoan().getSchedule().size() <= 0) {
            Assert.assertTrue(false, "loan Schedule data cannot be empty/null ");
        }
    }

    // Method to add references
    public static void addReferences(String userId) throws IOException {
        String requestPayload = collectReferenceRequest();
        Response response = collectReference.postWithResponseBody(userId, requestPayload, XpressData.xpressGenericHeaders());
        Assert.assertEquals(response.getStatusCode(), 200, "Collect Reference API failure due to invalid HTTP status code");
        AddReferenceResponsePojo responsePojo = response.as(AddReferenceResponsePojo.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode requestPayloadNode = objectMapper.readTree(requestPayload);
        Assert.assertEquals(requestPayloadNode.get("fullName").asText(), responsePojo.getFullName(), "Mismatch in fullName");
        Assert.assertEquals(requestPayloadNode.get("phoneNumber").asText(), responsePojo.getPhoneNumber(), "Mismatch in phoneNumber");
    }

    // Method to generate the reference request payload
    public static String collectReferenceRequest() throws JsonProcessingException {
        String fullName = generateRandomName(5) + " " + generateRandomName(5);
        String mobileNumber = "+91" + generateRandomPhoneNumber();
        String collectReferenceRequest = null;
        collectReferenceRequest = new StringTemplate(XpressData.COLLECT_REFERENCE_REQUEST)
                .replace("contactFullName", fullName)
                .replace("contactPhoneNumber", mobileNumber)
                .toString();
        return collectReferenceRequest;
    }

    // Method to submit contact references
    public static void submitContactReferences(String userId) throws IOException {
        while (true) {
            Map<String, Integer> referenceData = returnReferenceRequiredCount(userId);
            int references = referenceData.get("references");
            int requiredReferenceCount = referenceData.get("requiredReferenceCount");
            if (references >= requiredReferenceCount) {
                break;
            }
            addReferences(userId);
        }
        Response submitResponse = submitReference.postWithPathParamAndResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(submitResponse.getStatusCode(), 200, "Submit Reference API failure due to invalid HTTP status code");
    }

    public static void completeApplicationReviewStep(String userId, String type, String mobile, String panNumber, boolean qualifiedToAvailLoans,
                                                     boolean qualifiedByWhitelist) {
        CashLoanOfferingResponsePojo loanOfferingResponsePojo = verifyLoanQualifierResponse(type, mobile, panNumber, qualifiedToAvailLoans,
                qualifiedByWhitelist);
        String completeApplicationReviewRequest = new StringTemplate(APPLICATION_REVIEW_REQUEST).toString();
        Response completeApplicationReviewResponse = completeApplicationReview.postWithResponseBody(userId, XpressData.xpressGenericHeaders(), completeApplicationReviewRequest);
        OnboardingCaseStatusResponsePojo applicationReviewStep = completeApplicationReviewResponse.as(OnboardingCaseStatusResponsePojo.class);
        Assert.assertEquals(completeApplicationReviewResponse.getStatusCode(), 200, "Complete application review api failure due to invalid HTTP status code");
        Assert.assertEquals(loanOfferingResponsePojo.getProductOffering(), applicationReviewStep.getProductOffering(), "Product offering mismatch");
    }

    public static void getKfsData(String userId) {
        Response getKfsResponse = getKfsDetails.getWithResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(getKfsResponse.getStatusCode(), 200, "Get KFS failure due to invalid HTTP status code");
        GetKfsResponsePojo kfsResponsePojo = getKfsResponse.as(GetKfsResponsePojo.class);
        if (StringUtils.isBlank(kfsResponsePojo.getKfsPreviewUrl())) {
            Assert.assertTrue(false, "Empty/null KFS preview url in response");
        }
    }

    public static void signKfs(String userId) {
        Response response = signKfs.postWithPathParamAndResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(response.getStatusCode(), 200, "Sign KFS failure due to invalid HTTP status code");
        //OnboardingCaseStatusResponsePojo signKfsResponse = response.as(OnboardingCaseStatusResponsePojo.class);
    }

    public static void fetchLoanAgreement(String userId) throws Exception {
        int retryCount = 0;
        int maxRetries = 3;

        while (retryCount < maxRetries) {
            Response response = fetchLoanAgreement.putWithoutQueryParams(prepareFetchLoanAgreementRequest(userId), XpressData.xpressGenericHeaders());
            int statuscode = response.getStatusCode();
            FetchLoanAgreementResponsePojo FetchLoanAgreementResponse = response.as(FetchLoanAgreementResponsePojo.class);
            // Check if the response fields are not null
            if (statuscode == 200) {
                Assert.assertNotNull(FetchLoanAgreementResponse.preview_url, "preview_url should not be null");
                Assert.assertNotNull(FetchLoanAgreementResponse.otp_url, "otp should not be null");
                return; // Exit method if successful
            } else if (statuscode == 418) {
//                Assert.assertEquals(FetchLoanAgreementResponse.reason, "Please retry once and let us know if this happens again");
                retryCount++;
                System.out.println("Retrying due to request failure... (" + retryCount + ")");
            } else if(statuscode == 500){
                retryCount++;
                System.out.println("Retrying due to request failure... (" + retryCount + ")");
            }
            else
                Assert.fail("incorrect status code");
        }
        Assert.fail("Max retries reached due to request failure.");
    }

    public static String prepareFetchLoanAgreementRequest(String userId) {
        String fetchLoanAgreementRequest = null;
        fetchLoanAgreementRequest = new StringTemplate(XpressData.FETCH_LOAN_AGREEMENT_REQUEST).replace("userId", userId).toString();
        return fetchLoanAgreementRequest;
    }

    public static void sendAgreementOtp(String userId) throws Exception {
        Response submitResponse = agreementSendOtp.postWithResponse(userId, XpressData.xpressGenericHeaders());
        Assert.assertEquals(submitResponse.getStatusCode(), 200, "Agreement Send Otp api failure due to invalid HTTP status code");
    }

    public static void signLoanAgreement(String userId, String mobile) throws Exception {
        fetchLoanAgreement(userId);
        sendAgreementOtp(userId);
        String getOtp = agreementGetOtp(mobile);

        // Repeat until a valid OTP is received
        while (getOtp == null || getOtp.startsWith("0")) {
            fetchLoanAgreement(userId);
            sendAgreementOtp(userId);
            getOtp = agreementGetOtp(mobile);
        }
        OnboardingCaseStatusResponsePojo response = signLoanAgreement.put(prepareSignLoanAgreementRequest(userId, getOtp), XpressData.xpressGenericHeaders());
        Assert.assertEquals(response.getNextStep(), "WAIT", "next step is not wait");
        Assert.assertEquals(response.getWaitingForStep(), "APPLICATION_DISBURSAL", "Waiting for step is not APPLICATION_DISBURSAL");
        Assert.assertEquals(response.getLastCoveredApplicableCheckpoint(), "SIGN_LOAN_AGREEMENT", "Last covered applicable checkpoint is not SIGN_LOAN_AGREEMENT");
    }

    public static String prepareSignLoanAgreementRequest(String userId, String otp) {
        String prepareSignLoanAgreementRequest = null;
        prepareSignLoanAgreementRequest = new StringTemplate(XpressData.SIGN_LOAN_AGREEMENT_REQUEST)
                .replace("userId", userId)
                .replace("otp", otp)
                .toString();
        return prepareSignLoanAgreementRequest;
    }

    public static void OnboardingCaseStatus(String mobile) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("mobile", mobile);
        Response onboardingResponse = onboardingCaseStatus.getWithResponse(queryParams, XpressData.xpressGenericHeaders());
        Assert.assertEquals(onboardingResponse.getStatusCode(), 200, "Onboarding case status api failure.");
    }

    public static void verifyActiveStatusForFirstLoanUser(String mobile, String nextStepToCheck) throws SQLException, ClassNotFoundException {
        Assert.assertEquals(returnNextStep(mobile), nextStepToCheck, "Invalid next step for repeat user");
        Assert.assertEquals(fetchWhitelistingActiveStatusForFirstLoanUser(mobile), 0, "Users first loan whitelisting does not expired properly for repeat user creation.");
    }
}
