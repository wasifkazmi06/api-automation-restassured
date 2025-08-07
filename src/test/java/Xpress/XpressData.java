package Xpress;

import api.Xpress.*;
import api.ams.V1PlatformOTP;
import api.platform.GetUserData;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import dbUtils.Ocr_DBAccessObject;
import dbUtils.Payments_DBAccessObject;
import dbUtils.Pscore_DBAccessObject;
import dbUtils.Shylock_DBAccessObject;
import dbUtils.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static Xpress.PlatformService.returnUmuuid;

@Slf4j
public class XpressData {

    public XpressData() throws Exception {
    }

    // -------- Xpress DB Queries ---- //
//    String FETCH_AGREEMENT_OTP = "SELECT * from pscore_sandbox.users_otp WHERE phone_id = # order by created_at desc;";
//    String UPDATE_SENT_AT_COLUMN = "UPDATE pscore_sandbox.users_otp SET sent_at = $ WHERE phone_id = # order by created_at desc;";
    public static final String FETCH_MASTER_USER_ID = "select * from users_masteruser where phone_id = '+91$'";
    public static final String VALID_ENTRY_CHECK_SHYLOCK = "select * from preapproved_line  where phone_number = $";
    public static final String DELETE_ENTRY_PRE_APPROVED = "DELETE from preapproved_line where phone_number = $";
    public static final String DELETE_MOBILE_ENTRY_OCR_DB = "DELETE from pg_affluence_score where phone_number = '$'";
    public static final String FIND_MAX_ID = "select id from pg_affluence_score pas order by id desc limit 1";
    public static final String FETCH_APP_ID = "SELECT id FROM products_loanapplication pl WHERE pl.master_user_id = (SELECT um.id FROM users_masteruser um WHERE um.phone_id = '+91$') AND pl.status = 20 ORDER BY pl.updated_at DESC LIMIT 1";
    public static final String INSERT_PG_SCORE_OCR_DB = "INSERT INTO pg_affluence_score(id, phone_number, pg_id, affluence_score, days_on_pg, max_sale_amount_6month,ecommerce_sale_30," +
            "created_at, updated_at) VALUES(?,?,?,?,?,?,?,?,?)";

    public static final String FETCH_KYC_CASE_ID = "SELECT id from kyc_case WHERE uuid ='$' and active = 1";
    public static final String DELETE_KYCCASE_DOC_MAPPING_ADDRESSPROOF_DOC = "DELETE from kyc_case_doc_mapping WHERE kyc_case_id = '$' and doc_type_id = 'ADDRESS_PROOF_WITH_PHYSICAL_VERIFICATION' ";
    public static final String DELETE_KYC_USERDOCUMENTS_ADDRESSPROOF_DOC = "DELETE  from user_documents where uuid = '$' and document_type_id ='ADDRESS_PROOF_WITH_PHYSICAL_VERIFICATION' ";
    public static final String FETCH_SCRUBDATA_FOR_REPEAT_ASSESSMENT = "SELECT * \n" +
            "FROM generic_assessment_policy_request gapr\n" +
            "INNER JOIN generic_assessment_request gar ON gar.id=gapr.assessment_request_id\n" +
            "INNER JOIN generic_assessment_lead_details gald ON gald.id=gar.assessment_lead_details_id\n" +
            "INNER JOIN generic_assessment_lead gal ON gal.id=gald.assessment_lead_id\n" +
            "WHERE gald.lead_identifiers->>'data.user_data.pan'='$'\n" +
            "ORDER BY gapr.created_at desc limit 1";

    // --------- Xpress Request payload path ----- //
    public static final String WHITELIST_CSV = "src/test/resources/Xpress/Whitelist_user.csv";
    public static final String LAZYPAY_FEATURES_MOCK_REQUEST = "src/test/resources/Xpress/lazypayFeatureMock.txt";
    public static final String ONE_REPAYMENT_FEATURES_MOCK_REQUEST = "src/test/resources/Xpress/OneRepaymentMock.txt";
    public static final String BSCORE_FEATURES_MOCK_REQUEST = "src/test/resources/Xpress/BscoreMock.txt";
    public static final String DARWIN_V6_MOCK_REQUEST = "src/test/resources/Xpress/DarwinV6Mock.txt";
    public static final String DARWIN_V5_MOCK_REQUEST = "src/test/resources/Xpress/DarwinV5Mock.txt";
    public static final String CIBIL_CBP_MOCK_REQUEST = "src/test/resources/Xpress/CibilCbpMock.txt";
    public static final String UPDATE_BNPL_LIMIT_REQUEST = "src/test/resources/Xpress/UpdateCreditOverride.txt";
    public static final String USERREGISTRATION_REQUEST = "src/test/resources/Xpress/UserRegistration.txt";
    public static final String PAYMENT_API_REQUEST = "src/test/resources/Xpress/LoanPaymentApi.txt";
    public static final String DISBURSAL_API_REQUEST = "src/test/resources/Xpress/DisbursalApi.txt";
    public static final String OTP_V1_PLATFORM_REGISTRATION_REQUEST = "src/test/resources/Xpress/OtpV1Platform.txt";
    public static final String CIBIL_HARDPULL_MOCK_REQUEST = "src/test/resources/Xpress/CibilHardpullMock.txt";
    public static final String CASHLOAN_OFFERING_REQUEST = "src/test/resources/Xpress/CashLoanOfferingReq.txt";
    public static final String AGREEMENT_SENT_OTP_REQUEST = "src/test/resources/Xpress/AgreementSentOtp.txt";
    public static final String APPLICATION_REVIEW_REQUEST = "src/test/resources/Xpress/ApplicationReview.txt";
    public static final String BANK_VERIFY_REQUEST = "src/test/resources/Xpress/BankVerify.txt";
    public static final String COLLECT_REFERENCE_REQUEST = "src/test/resources/Xpress/CollectReference.txt";
    public static final String COMMUNICATION_ADDRESS_REQUEST = "src/test/resources/Xpress/CommunicationAddress.txt";
    public static final String FETCH_LOAN_AGREEMENT_REQUEST = "src/test/resources/Xpress/FetchLoanAgreement.txt";
    public static final String SIGN_LOAN_AGREEMENT_REQUEST = "src/test/resources/Xpress/SignLoanAgreement.txt";
    public static final String SELECT_LOAN_PLANS_REQUEST = "src/test/resources/Xpress/SelectLoanPlan.txt";
    public static final String PORTFOLIO_SCRUB_MOCK_REQUEST = "src/test/resources/Xpress/PortfolioScrub.txt";


    // --------- Xpress constants ----- //
    public static final String LAZYPAY_RCM_DECILE = "RCM_4";
    public static final String LAZYPAY_MAX_DPD = "0";
    public static final String LAZYPAY_CYCLE_ON_LP = "10";
    public static final String DARWIN_BUREAU_BANKING_PG_SCORE = "0.05";
    public static final String DARWIN_BUREAU_PG_SCORE = "0.1";
    public static final String DARWIN_BUREAU_SMS_PG_SCORE = "0.05";
    public static final String DARWIN_BUREAU_BANKING_SMS_PG_SCORE = "0.05";
    public static final String DARWIN_HAS_SMS = "false";
    public static final String DARWIN_HAS_BUREAU = "true";
    public static final String DARWIN_HAS_BANKING = "false";
    public static final String DARWIN_HAS_PG = "true";
    public static final String BUREAU_SCORE = "800";
    public static Double PG_AFFLUENCE_SCORE = 90.0;
    public static Integer DAYS_ON_PG = 360;
    public static Double MAX_SALE_AMOUNT_6M = 0.0;
    public static Double ECOMMERCE_SALE_3M = 0.0;

    // ---------BNPL_XSELL ----- //
    public static final String BNPL_XSELL_BUREAU_PG_SCORE = "0.138";
    public static final String BNPL_XSELL_BUREAU_BANKING_PG_SCORE = "0.064";
    public static final String BNPL_XSELL_BUREAU_SMS_PG_SCORE = "0.11301696999769982";
    public static final String BNPL_XSELL_BUREAU_BANKING_SMS_PG_SCORE = "0.10824546962976456";
    public static final String BNPL_XSELL_HAS_BUREAU = "true";
    public static final String BNPL_XSELL_HAS_PG = "true";
    public static final String BNPL_XSELL_HAS_SMS = "false";
    public static final String BNPL_XSELL_HAS_BANKING = "false";
    public static final String BNPL_XSELL_CYCLE_ON_LP = "10";

    // ---------BNPL_XSELL_ONE_REPAYMENT ----- //

    public static final String BNPL_XSELL_ONE_REPAYMENT_CYCLE_ON_LP = "1";
    public static final String DARWIN_1REPAYMENT_BUREAU_PG_SCORE = "0.05";


    // ---------BUREAU_SURROGATE ----- //
    public static final String BUREAU_SURROGATE_BUREAU_PG_SCORE = "0.05";
    public static final String BUREAU_SURROGATE_BUREAU_BANKING_PG_SCORE = "0.064";
    public static final String BUREAU_SURROGATE_BUREAU_SMS_PG_SCORE = "0";
    public static final String BUREAU_SURROGATE_BUREAU_BANKING_SMS_PG_SCORE = "0";
    public static final String BUREAU_SURROGATE_HAS_BUREAU = "true";
    public static final String BUREAU_SURROGATE_HAS_PG = "true";
    public static final String BUREAU_SURROGATE_HAS_SMS = "false";
    public static final String BUREAU_SURROGATE_HAS_BANKING = "false";
    public static final String BUREAU_SURROGATE_CYCLE_ON_LP = "10";

    // ---------STPL ----- //
    public static final String STPL_BUREAU_SCORE = "600";

    // ---------STATED_INCOME ----- //
    public static Double SI_PG_AFFLUENCE_SCORE = 0.0;


    public static final String USER_NOT_EXISTS_MSG = "User with this identifier doesnt exist";
    public static final String PRODUCT_OFFERING = "XPRESS";
    public static final String BUREAU_AND_CKYC_CONSENT_VALUE = "I agree to Credit Bureau and CKYC consent";
    public static final String NONMICRO_FINANCE_CONSENT_VALUE = "I hereby confirm to PayU Finance India Private Limited that my annual household income is more than INR 3,00,000/- (Rupees Three Lakh), that is, on an average more than INR 25,000/- (Rupees Twenty-Five Thousand) per month. For purpose of this declaration, I have considered income of myself, my spouse and our unmarried children";
    public static final String REASON_FOR_LOAN = "Renovation";
    public static final String SOURCE_OF_SUBMIT_PANFORM = "Renovation";
    public static final String NEXT_STEP_AFTERLOANPLAN_SELECT = "OVERVIEW";
    public static final String LAST_COVERED_CHECKPOINT_AFTER_SELECTLOANPLAN = "KYC";
    public static final String LAST_COVERED_APPLICABLE_CHECKPOINT_AFTER_SELECTLOANPLAN = "KYC";
    public static final String LAST_COVERED_CHECKPOINT_AFTER_CA_FORM = "COMMUNICATION_ADDRESS";
    public static final String LAST_COVERED_APPLICABLE_CHECKPOINT_AFTER_CA_FORM = "COMMUNICATION_ADDRESS";
    public static final String LAST_COVERED_CHECKPOINT_AFTER_SKIP_REPAYMENT = "SETUP_REPAYMENT";
    public static final String LAST_COVERED_APPLICABLE_CHECKPOINT_AFTER_SKIP_REPAYMENT = "SETUP_REPAYMENT";
    public static final String SUCCESS_PANFORM_SUBMISSION_MESSAGE = "LazyPay is creating the best credit limit for you!";
    public static final String ASSESSMENT_TYPE_REPEAT ="xpress-repeat";


    static CreditExpire creditExpirePscoreData;
    static CibilCbpMocking cibilCbpMocking;
    static BscoreMocking bscoreMocking;
    static CibilHardpullMocking cibilHardpullMocking;
    static CibilPortfolioMocking cibilPortfolioMocking;
    static DarwinV6Mocking darwinV6Mocking;
    static DarwinV5Mocking darwinV5Mocking;
    static LazypayMocking lazypayMocking;
    static OneRepaymentMocking OneRepaymentMocking;
    static ShylockApi shylockApi;
    static FetchUserStatus fetchUserStatus;
    static RiskUpdateCreditOverride riskUpdateCreditOverride;
    static PscoreRegistration pscoreRegistration;
    static DisbursalAPi userDisbursalAPi;
    static V1PlatformOTP v1PlatformOTP;
    static GetUserData getUserData;
    static FetchMaxDPD fetchMaxDPD;
    static PaymentAPI paymentAPI;
    static CashLoanOffering cashLoanOffering;
    static GetPanDetails getPanDetails;
    static SubmitPanForm submitPanForm;
    static OnboardingCaseStatus onboardingCaseStatus;
    static GetLoanPlans getLoanPlans;
    static SelectLoanPlan selectLoanPlan;
    static JourneyOverview journeyOverview;
    static VerifyBankAccount verifyBankAccount;
    static SkipRepayment skipRepayment;
    static SelectedLoanPlanDetails selectedLoanPlanDetails;
    static CompleteApplicationReview completeApplicationReview;
    static SignKfs signKfs;
    static GetKfsDetails getKfsDetails;
    static FetchLoanAgreement fetchLoanAgreement;
    static AgreementSendOtp agreementSendOtp;
    static SubmitCaForm submitCaForm;
    static SignLoanAgreement signLoanAgreement;
    static CollectReference collectReference;
    static CheckReferenceRequiredCount checkReferenceRequiredCount;
    static SubmitReference submitReference;
    static AgreementGetOtp agreementGetOtp;
    static PscoreGenericAssessmentApi pscoreGenericAssessmentApi;
    static ScrubAssessment scrubAssessment;
    static RepeatAssessment repeatAssessment;

    static {
        try {
            creditExpirePscoreData = new CreditExpire();
            cibilCbpMocking = new CibilCbpMocking();
            bscoreMocking = new BscoreMocking();
            cibilHardpullMocking = new CibilHardpullMocking();
            cibilPortfolioMocking = new CibilPortfolioMocking();
            darwinV6Mocking = new DarwinV6Mocking();
            darwinV5Mocking = new DarwinV5Mocking();
            lazypayMocking = new LazypayMocking();
            OneRepaymentMocking = new OneRepaymentMocking();
            shylockApi = new ShylockApi();
            fetchUserStatus = new FetchUserStatus();
            riskUpdateCreditOverride = new RiskUpdateCreditOverride();
            pscoreRegistration = new PscoreRegistration();
            v1PlatformOTP = new V1PlatformOTP();
            getUserData = new GetUserData();
            userDisbursalAPi = new DisbursalAPi();
            fetchMaxDPD = new FetchMaxDPD();
            paymentAPI = new PaymentAPI();
            cashLoanOffering = new CashLoanOffering();
            getPanDetails = new GetPanDetails();
            submitPanForm = new SubmitPanForm();
            onboardingCaseStatus = new OnboardingCaseStatus();
            getLoanPlans = new GetLoanPlans();
            selectLoanPlan = new SelectLoanPlan();
            journeyOverview = new JourneyOverview();
            verifyBankAccount = new VerifyBankAccount();
            skipRepayment = new SkipRepayment();
            selectedLoanPlanDetails = new SelectedLoanPlanDetails();
            completeApplicationReview = new CompleteApplicationReview();
            signKfs = new SignKfs();
            getKfsDetails = new GetKfsDetails();
            fetchLoanAgreement = new FetchLoanAgreement();
            agreementSendOtp = new AgreementSendOtp();
            submitCaForm = new SubmitCaForm();
            signLoanAgreement = new SignLoanAgreement();
            collectReference = new CollectReference();
            checkReferenceRequiredCount = new CheckReferenceRequiredCount();
            submitReference = new SubmitReference();
            agreementGetOtp = new AgreementGetOtp();
            pscoreGenericAssessmentApi = new PscoreGenericAssessmentApi();
            scrubAssessment = new ScrubAssessment();
            repeatAssessment = new RepeatAssessment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> xpressGenericHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public static void purgeUser(String mobileNumber, String db) throws InterruptedException {
        try {
            Map<String, Object> queryParamDetails = new HashMap<>();
            HashMap<String, String> headerDetails = new HashMap<>();
            queryParamDetails.put("MobileNumber", mobileNumber);
            queryParamDetails.put("token", "PuRgE_U5eR");

            // Set the specified database to true and others to false
            String[] databases = {"kyc", "ds", "vl", "cl", "upi", "col", "sl", "lp", "rbl", "lpup", "bifrost", "fintech", "bbps", "urs", "auth", "devicebinding", "platform", "delete_UMUUID", "Purge_All_User_Data"};
            for (String database : databases) {
                if (database.equals(db)) {
                    queryParamDetails.put(database, "true");
                } else {
                    queryParamDetails.put(database, "false");
                }
            }

            Response response = RestAssured.given()
                    .headers(headerDetails)
                    .queryParams(queryParamDetails)
                    .get("http://rakeshgouda_patil:1136335a6648e3101bea8be843354c2f01@qa-jenkins.lazypay.net:8080/job/Purge_User/buildWithParameters").prettyPeek();
            Thread.sleep(10000);
            log.info("Shylock purge done successfully");
        } catch (Exception e) {
            Assert.assertTrue(false, "Unable to shylock purge user data with error " + e.getMessage());
        }
    }


    public static String getMaterUserId(String mobileNumber, String id) throws Exception {
        String muid = "";
        ResultSet rs = Pscore_DBAccessObject.selectFromDb(FETCH_MASTER_USER_ID.replace("$", mobileNumber));
        while (rs.next()) {
            muid = rs.getString(id);
        }
        return muid;
    }

    public static String getLoanId(String mobileNumber) throws Exception {
        String loanId = "";
        ResultSet rs = Pscore_DBAccessObject.selectFromDb(FETCH_APP_ID.replace("$", mobileNumber));
        while (rs.next()) {
            loanId = rs.getString("id");
        }
        return loanId;
    }

    public static Boolean checkIfValidWhitelistingDone(String mobileNumber) throws SQLException, ClassNotFoundException {
        Boolean validation_error = false;
        ResultSet rs = Shylock_DBAccessObject.selectFromDb(VALID_ENTRY_CHECK_SHYLOCK.replace("$", mobileNumber));
        while (rs.next()) {
            String validation_errors = rs.getString("validation_errors");
            if (validation_errors.isEmpty()) {
                validation_error = true;
            }
        }
        return validation_error;
    }

    public static void updateCSV(String type, String inputFile, String fullName, String panNUmber, String mobielNumber,
                                 String assessmentLeadID, String isUPI, String creditline) throws IOException {

        try {
            CSVReader csvReader = new CSVReader(new FileReader(new File(inputFile)));
            List<String[]> allData = csvReader.readAll();
            switch (type) {
                case "PRE_APPROVAL":
                    allData.get(1)[0] = fullName.trim();
                    allData.get(1)[1] = panNUmber.trim();
                    allData.get(1)[2] = mobielNumber.trim();
                    allData.get(1)[6] = assessmentLeadID.trim();
                    allData.get(1)[9] = isUPI.trim();
                    allData.get(1)[11] = creditline.trim();
                    CSVWriter writer = new CSVWriter(new FileWriter(new File(inputFile)), ',',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END);
                    writer.writeAll(allData);
                    ;
                    writer.flush();
                    break;
                case "PRE_SELECTION":
                    allData.get(1)[0] = fullName.trim();
                    allData.get(1)[1] = panNUmber.trim();
                    allData.get(1)[2] = mobielNumber.trim();
                    writer = new CSVWriter(new FileWriter(new File(inputFile)), ',',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END);
                    writer.writeAll(allData);
                    writer.flush();
                    break;

                case "LAZY_CARD":
                    allData.get(1)[0] = fullName.trim();
                    allData.get(1)[1] = panNUmber.trim();
                    allData.get(1)[2] = mobielNumber.trim();
                    allData.get(1)[10] = "TRUE";
                    writer = new CSVWriter(new FileWriter(new File(inputFile)), ',',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END);
                    writer.writeAll(allData);
                    ;
                    writer.flush();
                    break;
                default:
                    System.out.println("It should be either one flow");
            }
        } catch (Exception e) {
            Assert.assertTrue(false, "Exception coming while updating CSV with message " + e.getMessage());
        }
    }

    public static Integer fetchLatestId() throws SQLException, ClassNotFoundException {
        String id = null;
        ResultSet rs = Ocr_DBAccessObject.selectFromDb(FIND_MAX_ID);
        while (rs.next()) {
            id = rs.getString("id");
        }
        Integer newIdvalue = Integer.valueOf(id) + 1;
        if (newIdvalue == null) {
            Assert.assertTrue(false, "Error while fetching latest id from ocr db.");
        }
        return newIdvalue;
    }

    public static void updateLoans(List<Integer> loanIds) throws SQLException, ClassNotFoundException {
        // Build the query string
        StringBuilder queryBuilder = new StringBuilder("UPDATE loans_loandata_latest SET principal_outstanding = 0, penal_due = 0 WHERE loan_id IN (");
        for (int i = 0; i < loanIds.size(); i++) {
            queryBuilder.append(loanIds.get(i));
            if (i < loanIds.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(");");
        String query = queryBuilder.toString();

        try {
            // Execute the update query
            int count = Payments_DBAccessObject.updateOnDbandReturnCount(query);
            if (count == 0)
                Assert.fail("no rows were updated");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }


    public static Date getDateFromLocalDate() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate currentDate = LocalDate.now();
        Date date = Date.from(currentDate.atStartOfDay(defaultZoneId).toInstant());
        return date;
    }

    public static String generateRandomPhoneNumber() {
        Random random = new Random();

        // Array of valid starting digits (7, 8, 9)
        int[] validStartDigits = {7, 8, 9};
        int startDigit = validStartDigits[random.nextInt(validStartDigits.length)];

        // Generate the remaining 9 digits
        StringBuilder sb = new StringBuilder();
        sb.append(startDigit);

        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));  // Append a random digit from 0 to 9
        }

        return sb.toString();
    }

    public static String generateRandomName(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random RANDOM = new Random();
        StringBuilder name = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            name.append(CHARACTERS.charAt(index));
        }
        return name.toString();
    }

    public static Date addDaysInDate(Integer noOfDays) {
        Date initialDate = getDateFromLocalDate();

        // Add days to the java.util.Date
        int daysToAdd = noOfDays;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        initialDate = calendar.getTime();
        return initialDate;
    }

    public static String convertDate(String dateStr, String outputFormat) {
        List<String> dateFormats = new ArrayList<>();
        dateFormats.add("EEE MMM dd HH:mm:ss z yyyy");
        dateFormats.add("dd-MM-yyyy");
        dateFormats.add("MM-dd-yyyy");
        dateFormats.add("yyyy-MM-dd");
        dateFormats.add("dd/MM/yyyy");
        dateFormats.add("MM/dd/yyyy");
        dateFormats.add("MMMM d, yyyy");
        dateFormats.add("d MMMM, yyyy");
        // Add more date formats if needed

        Date date = null;
        for (String format : dateFormats) {
            try {
                SimpleDateFormat inputDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                inputDateFormat.setLenient(false);  // Strict parsing
                date = inputDateFormat.parse(dateStr);
                break;
            } catch (ParseException e) {
                // Continue to the next format if parsing fails
            }
        }

        if (date == null) {
            return "Error: Unparseable date: " + dateStr;
        }

        try {
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
            return outputDateFormat.format(date);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public static String fetchAddressByPincode(String pincode) throws Exception {
        String API_URL = "https://api.postalpincode.in/pincode/";
        String urlString = API_URL + pincode;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Check if the response code is 200 (HTTP_OK)
        if (conn.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString(); // Return the response as a string
        } else {
            throw new Exception("Failed to fetch data: HTTP error code " + conn.getResponseCode());
        }
    }

    public static void deleteKycDataAfterApproval(String mobile) throws SQLException, ClassNotFoundException {
        String kycId = fetchKycCaseIdForUser(mobile);
        KYC_MySQLSsh_DBAccessObject.deleteOnMySqlDbForXpress(DELETE_KYCCASE_DOC_MAPPING_ADDRESSPROOF_DOC.replace("$", kycId));
        KYC_MySQLSsh_DBAccessObject.deleteOnMySqlDbForXpress(DELETE_KYC_USERDOCUMENTS_ADDRESSPROOF_DOC.replace("$", returnUmuuid(mobile)));
    }

    public static String fetchKycCaseIdForUser(String mobile) throws SQLException, ClassNotFoundException {
        String kycCaseId = null;
        ResultSet rs = KYC_MySQLSsh_DBAccessObject.selectFromMySqlDbForXpress(FETCH_KYC_CASE_ID.replace("$", returnUmuuid(mobile)));
        while (rs.next()) {
            kycCaseId = rs.getString("id");
        }
        return kycCaseId;
    }


    public static int fetchWhitelistingActiveStatusForFirstLoanUser(String mobile) throws SQLException, ClassNotFoundException {
        int isActive = 0;
        ResultSet rs = Shylock_DBAccessObject.selectFromDb(VALID_ENTRY_CHECK_SHYLOCK.replace("$", mobile));
        while (rs.next()) {
            isActive = Integer.parseInt(rs.getString("is_valid"));
        }
        return isActive;
    }

    public static String returnResponseBodyInString(Response apiResponse) {
        String responseInStringFormat = null;
        // Retrieve the body of the Response
        ResponseBody body = apiResponse.getBody();

        //Convert reponse body in String format
        responseInStringFormat = body.asString();
        return responseInStringFormat;
    }

    public static String getJsonPath(Response response, String key) {
        JsonPath jsonPath = response.getBody().jsonPath();
        return jsonPath.get(key).toString();
    }

    public static HashMap<String, String> xpressAssessmentEngineHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("client-name", "PS");
        headers.put("client-token", "test");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public static HashMap<String, String> xpressArthamaticsHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("client-name", "PS");
        headers.put("client-token", "test");
        headers.put("token", "jJ36qep6vdhzocZE");
        headers.put("Content-Type", "application/json");
        return headers;
    }

    public static String fetchAssessmentLeadId(String panNumber) throws Exception {
        String assessmentLeadId = null;
        ResultSet rs = Ams_PostgreSql_DBAccessObject.selectFromDb(FETCH_SCRUBDATA_FOR_REPEAT_ASSESSMENT.replace("$", panNumber));
        while (rs.next()) {
            assessmentLeadId = rs.getString("assessment_lead_id");
        }
        return assessmentLeadId;
    }

    public static String fetchRequestData(String panNumber) throws Exception {
        String requestData = null;
        ResultSet rs = Ams_PostgreSql_DBAccessObject.selectFromDb(FETCH_SCRUBDATA_FOR_REPEAT_ASSESSMENT.replace("$", panNumber));
        while (rs.next()) {
            requestData = rs.getString("request_data");
        }
        return requestData;
    }
}