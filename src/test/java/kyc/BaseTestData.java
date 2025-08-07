package kyc;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import platform.PlatformSupportData;
import util.StringTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import pojos.kyc.digilocker.RequestTestData;
import pojos.neobank.fd.Configuration;
import util.readYml;
import static util.readYml.readYamlFile;
@Slf4j
public class BaseTestData extends PlatformSupportData  {

    public static final String TEST_USER_UUID = "5831717265125545849";
    public static final File PAN_DOC = new File(KycConstants.PAN_UPLOAD_JSON);
    public static final File SELFIE_DOC = new File(KycConstants.SELFIE_UPLOAD_JSON);
    public static final File PERSONAL_DETAILS_INCOME_FORM = new File(KycConstants.PERSONAL_DETAILS_INCOME_FORM_JSON);
    public static final File USER_REVIEW_DETAILS = new File(KycConstants.USER_REVIEW_DETAILS);
    public static final File LOCATION_DETAILS = new File(KycConstants.LOCATION_DETAILS);
    public static String USER_MOBILE = "6000000171";
    public static String VALID_PANDOC_TYPE_ID="PAN_DOC";
    public static String INVALID_DOC_TYPE_ID="ABC";
    public static String PAN_dataJson =
            "{\"PAN_CONSENT_FIELD\":\"true\",\"PAN_NAME\":\"Ambati Jyothi\",\"DOB\":\"714138656\",\"PAN_NO\":\"AAAAS0515A\"}";
    public static String Selfile_dataJson;
    public static String INCOMEFORM_JSON =
            "{\"EMPLOYMENT_TYPE\":\"Private-job\",\"FATHER_NAME\":\"Veera \",\"INCOME_RANGE\":\"₹5Lakhs+\",\"GENDER\":\"FEMALE\"}";

    public static String LOCATION_JSON = "{\"LATITUDE\":\"21.4733819\",\"LONGITUDE\":\"80.1946512\"}";

    /*  public static String INCOMEFORM_JSON =
                 "{\"EMPLOYMENT_TYPE\":\"Private-job\",\"FATHER_NAME\":\"YFS \",\"INCOME_RANGE\":\"₹5Lakhs+\",\"GENDER\":\"FEMALE\"}";
         public static String PAN_dataJson =
                 "{\"PAN_CONSENT_FIELD\":\"true\",\"PAN_NAME\":\"Kareeshma Sharif\",\"DOB\":\"742268344\",\"PAN_NO\":\"EDHPS2950H\"}";

     */
    public static final File APV_DOC = new File(KycConstants.APV_UPLOAD_JSON);
    public static String APV_dataJson =
            "{\"type\": \"CURRENT_LOCATION\",\"lat\": 15.9094349,\"lng\": 75.667356}";
    public static String LOCATION_JSON2 = "{\"LATITUDE\":\"15.9094349\",\"LONGITUDE\":\"75.667356\"}";

    // Test data for UUID field
    public static final String VALID_USER_UUID = "5831717265125545849";
    public static final String INVALID_USER_UUID = "8825381116923957229";
    public static final String EMPTY_USER_UUID = "";
    // Test data for product field
    public static final String VALID_PRODUCT = "PAYUFIN_KYC";
    public static final String INVALID_PRODUCT = "PAYUFIN";
    public static final String EMPTY_PRODUCT = "";
    // Test data for source field
    public static final String VALID_SOURCE_BNPL = "BNPL";
    public static final String VALID_SOURCE_XPRESS = "XPRESS";
    public static final String EMPTY_SOURCE = "";
    public static final String INVALID_SOURCE = "abcd";


    // Test data for PAN form
    public static final String VALID_PAN_NUMBER = "AAAAS0515A";
    public static final String INVALID_PAN_NUMBER = "AAAAB4451L";
    public static final String DUPLICATE_PAN_NUMBER = "AAAAS0515A";

    // Test data for kycCaseId
    public static final String INVALID_KYC_CASE_ID = "12345";
    public static final String EMPTY_KYC_CASE_ID = "";
    // Test data for selfie
    public static final String selfieJson = getSelfieJsonData();
    //public static String VALID_SELFIE_DOC_TYPE_ID="IPV_SELFIE";
    // Test data for personal details
    public static final String EMPLOYMENT_TYPE = "Private-job";
    public static final String INCOME_RANGE = "₹5Lakhs+";
    public static final String FATHER_NAME = "Veera";
    public static final String GENDER = "FEMALE";

    public static final String PAN_DELINKED_USER = "5189699679495393484";
    public static final String DELINKED_PAN = "EDHPS2950H";
    public static String PAN_DELINK_MOBILE1 = "9986611041";
    public static String PAN_DELINK_MOBILE2 = "7760703178";

    public static String PAN_UPLOADED_USER = "4983900123129576841";
    public static String BNPL_MITC_SIGNED_USER = "9164755759966214472";
    public static String XPRESS_MITC_SIGNED_USER = "4887150531027606466";
    public static String SOURCE1 = "BNPL";
    public static String SOURCE2 = "XPRESS";
    public static String KYC_TYPE1 = "CKYC";
    public static String KYC_TYPE2 = "AADHAAR_OTP";

    private static final String SIGN_MITC_REQUEST = "src/test/resources/kyc/apis/signMITC.txt";

    //public static RequestTestData requestTestData;


    static RequestTestData requestTestData;

    static {
        try {
            requestTestData = readYamlFile(Configuration.class,"src/test/resources/kyc/digilocker/digilocker.yml").getDigilocker_data();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public BaseTestData() throws Exception {
        super();
    }

    public static String getSelfieJsonData() {
        try {
            Selfile_dataJson = IOUtils.toString(new FileInputStream("src/test/resources/kyc/apis/selfiedatajson.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Selfile_dataJson;
    }

    // data provider for uuid
    @SneakyThrows
    @DataProvider(name = "uuid&product_testdata")
    public final static Object[][] getTestUuid()
    {
        return new Object[][] {{VALID_USER_UUID,VALID_PRODUCT},
                {INVALID_USER_UUID,VALID_PRODUCT},
                {EMPTY_USER_UUID,VALID_PRODUCT},
                {VALID_USER_UUID,INVALID_PRODUCT},
                {VALID_USER_UUID,EMPTY_PRODUCT},
                {null,VALID_PRODUCT},
                {VALID_USER_UUID,null}
        };
    }
    // data provider for PAN-DOC upload
    @SneakyThrows
    @DataProvider(name = "PAN-DOC Testdata")
    public final static Object[][] getTestPanDoc()
    {
        return new Object[][] {{"INVALID_PAN_NUMBER",INVALID_PAN_NUMBER},
                {"VALID_PAN_NUMBER",VALID_PAN_NUMBER },
                {"DUPLICATE_PAN_NUMBER",DUPLICATE_PAN_NUMBER}
        };
    }
    // data provider for uploadDocuments API
    @SneakyThrows
    @DataProvider(name = "kycCaseId testdata")
    public final static Object[][] getInvalidKycCaseData(){

        return new Object[][] {{INVALID_KYC_CASE_ID},{EMPTY_KYC_CASE_ID}};
    }
    // data provider for IPV_SELFIE upload
    @SneakyThrows
    @DataProvider(name = "SELFIE Testdata")
    public final static Object[][] getTestSelfieDoc()
    {
        return new Object[][] {{"INVALID_SELFIE","SELFIE"},
                {"VALID_SELFIE", selfieJson},
                {"RE_UPLOAD_SELFIE",selfieJson}
        };
    }
    // data provider for personal details form
    @SneakyThrows
    @DataProvider(name = "incomeFormTestData")
    public final static Object[][] getIncomeformTestData()
    {
        return new Object[][] {{"VALID_INCOME_RANGE",INCOME_RANGE},
                {"EMPTY_INCOME_RANGE",""}};
    }
    // data provider for invalid employment_type field
    @SneakyThrows
    @DataProvider(name = "employmentTypeTestData")
    public final static Object[][] getEmploymentTypeTestData()
    {
        return new Object[][] {{"INVALID_EMPLOYMENT_TYPE",INCOME_RANGE},
                {"EMPTY_EMPLOYMENT_TYPE",""}};
    }
    @SneakyThrows
    @DataProvider(name = "getPanDelinkTestdata")
    public final static Object[][] getPanDelinkTestdata()
    {
        return new Object[][] {
                {"Invalid_UUID", INVALID_USER_UUID, DELINKED_PAN},
                {"Invalid_PANNUMBER", "5189699679495393484", INVALID_PAN_NUMBER},
                //     {"WithNo_PANNUMBER","5189699679495393484",""},
                //    {"WithNo_UUID","",DELINKED_PAN},
                {"Already_Delinked_user", PAN_DELINKED_USER, DELINKED_PAN},

        };
    }

    @SneakyThrows
    @DataProvider(name = "ckycReportingTestdata")
    public final static Object[][] ckycReportingTestdata() {
        return new Object[][]{
                //      {"Valid_Uuid&Product",VALID_USER_UUID,VALID_PRODUCT},
                {"Invalid_UUID", INVALID_USER_UUID, VALID_PRODUCT},
                {"Invalid_PRODUCT", VALID_USER_UUID, VALID_PRODUCT},
                {"Invalid_Uuid&Product", INVALID_USER_UUID, VALID_PRODUCT},
                {"WithNOUuid", "null", VALID_PRODUCT},
                {"WithNOProduct", VALID_USER_UUID, "null"},

        };
    }
    // data provider for V4/KycStatus API
    @SneakyThrows
    @DataProvider(name = "V4/kycstatus Testdata")
    public final static Object[][] getV4KycStatusTestdata() {
        return new Object[][]{{"VALID_UUID", VALID_USER_UUID, VALID_SOURCE_BNPL},
                {"INVALID_UUID", INVALID_USER_UUID, VALID_SOURCE_BNPL},
                {"EMPTY_UUID", EMPTY_USER_UUID, VALID_SOURCE_BNPL} ,
                {"NOT_INITIATED", VALID_USER_UUID, VALID_SOURCE_BNPL},
                {"INVALID_SOURCE", VALID_USER_UUID, INVALID_SOURCE},
                {"EMPTY_SOURCE", VALID_USER_UUID, EMPTY_SOURCE}
        };
    }

    // data provider for v1/latest-success-kyc API
    @SneakyThrows
    @DataProvider(name = "V1/latestSuccessKyc Testdata")
    public final static Object[][] getV1LatestSuccessKycTestdata() {
        return new Object[][]{
                {"INVALID_UUID", INVALID_USER_UUID, VALID_SOURCE_BNPL},
                {"EMPTY_UUID", EMPTY_USER_UUID, VALID_SOURCE_BNPL} ,
                {"NOT_INITIATED", VALID_USER_UUID, VALID_SOURCE_BNPL},
                {"INVALID_SOURCE", VALID_USER_UUID, INVALID_SOURCE},
                {"EMPTY_SOURCE", VALID_USER_UUID, EMPTY_SOURCE}
        };
    }

    // data provider for source
    @SneakyThrows
    @DataProvider(name = "source_testdata")
    public final static Object[][] getTestSourceData() {
        return new Object[][]{{"VALID_SOURCE_BNPL", VALID_SOURCE_BNPL},
                {"INVALID_SOURCE", "abc"},
                {"EMPTY_SOURCE", EMPTY_SOURCE},
                {"VALID_SOURCE_XPRESS",VALID_SOURCE_XPRESS},
                {"NO_SOURCE_FEILD",null}
        };}

    @SneakyThrows
    @DataProvider(name = "panDetailsTestdata")
    public final static Object[][] panDetailsTestdata ()
    {
        return new Object[][]{
                {"ValidUuid", PAN_UPLOADED_USER},
                {"IncorrectUuid", INVALID_USER_UUID + "test"},
                {"WithNullUuid", "null"},
                {"UuidWithNoPanUploaded", INVALID_USER_UUID}

        };
    }
    @SneakyThrows
    @DataProvider(name = "eligibilityTestdata")
    public final static Object[][] eligibilityTestdata ()
    {
        return new Object[][]{
                {"ValidUuid", PAN_UPLOADED_USER, VALID_PRODUCT},

        };
    }

    @SneakyThrows
    @DataProvider(name = "v2CaseDetailsTestdata")
    public final static Object[][] v2CaseDetailsTestdata()
    {
        return new Object[][] {
                {"ValidParams",BNPL_MITC_SIGNED_USER,KYC_TYPE1,true,VALID_PRODUCT},
                {"WithKYCType_CKYC",BNPL_MITC_SIGNED_USER,KYC_TYPE1,true,VALID_PRODUCT},
                {"WithKYCType_AADHHAR",BNPL_MITC_SIGNED_USER,KYC_TYPE2,true,VALID_PRODUCT},
                {"WithFullValue_False",BNPL_MITC_SIGNED_USER,KYC_TYPE2,false,VALID_PRODUCT},
                {"IncorrectUuid",INVALID_USER_UUID,KYC_TYPE1,true,VALID_PRODUCT},
                {"WithNullUuid","null",KYC_TYPE1,true,VALID_PRODUCT},
                {"InvalidProduct",BNPL_MITC_SIGNED_USER,KYC_TYPE1,true,"LP"},
                {"WithNullProduct",BNPL_MITC_SIGNED_USER,KYC_TYPE1,true,"null"},

        };
    }
    @SneakyThrows
    @DataProvider(name = "v3CaseDetailsTestdata")
    public final static Object[][] v3CaseDetailsTestdata()
    {
        return new Object[][] {
                {"ValidUuid&Source_BNPL",TEST_USER_UUID,SOURCE1},
                //          {"ValidUuid&Source_XPRESS",XPRESS_MITC_SIGNED_USER,SOURCE2},
                {"IncorrectUuid",INVALID_USER_UUID,SOURCE1},
                {"WithNullUuid","null",SOURCE1},
                {"InvalidSource",BNPL_MITC_SIGNED_USER,"LP"},
                {"WithNullSource",BNPL_MITC_SIGNED_USER,"null"},

        };
    }
    @SneakyThrows
    @DataProvider(name = "getCkycNumberTestdata")
    public final static Object[][] getCkycNumberTestdata() {
        return new Object[][]{
                {"ValidUuid&Product", BNPL_MITC_SIGNED_USER, VALID_PRODUCT},
                {"CKYC_FailedUser", PAN_UPLOADED_USER, VALID_PRODUCT},
                {"NonKYC'dUuid", PAN_DELINKED_USER, VALID_PRODUCT},
                {"InvalidProduct", BNPL_MITC_SIGNED_USER, "LP"},
                {"WithNullProduct", BNPL_MITC_SIGNED_USER, "null"},

        };
    }

    // data provider for source,kycCaseId & uuid
    @SneakyThrows
    @DataProvider(name = "deactivateKycCasetestdata")
    public final static Object[][] getTestData() {
        return new Object[][]{
                {"INVALID_SOURCE", INVALID_SOURCE,TEST_USER_UUID},
                {"EMPTY_SOURCE", EMPTY_SOURCE,TEST_USER_UUID},
                {"EMPTY_UUID", VALID_SOURCE_BNPL,EMPTY_USER_UUID},
                {"NO_SOURCE_FEILD", null,TEST_USER_UUID},
                {"NO_UUID_FEILD",VALID_SOURCE_BNPL,null}
        };
    }
    public String signMITCRequestPayload(String uuid, String userId) {
        String mitcPayload = new StringTemplate(SIGN_MITC_REQUEST)
                .replace("umUuid", uuid)
                .replace("userid", userId)
                .toString();
        return mitcPayload;
    }
    // purge user generic method
    //  @Step
    @SneakyThrows
    public void purgeUser(String mobileNumber){
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        queryParamDetails.put("MobileNumber", mobileNumber);
        queryParamDetails.put("token", "PuRgE_U5eR");
        queryParamDetails.put("Purge_All_User_Data", "false");
        queryParamDetails.put("kyc", "true");
        Response response = RestAssured.given()
                .headers(headerDetails)
                .queryParams(queryParamDetails)
                .get("http://rakeshgouda_patil:1136335a6648e3101bea8be843354c2f01@qa-jenkins.lazypay.net:8080/job/Purge_User/buildWithParameters").prettyPeek();
        Thread.sleep(10000);
        log.info("KYC Method calling");
    }
    @SneakyThrows
    @DataProvider(name = "startLinkRequestDate")
    public final static Object[][] startLinkRequestDate() {

        return new Object[][]{
                {"WITH_VALID_APIKEY",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_VALID_SOURCE_LP", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_VALID_SOURCE_PS", requestTestData.getPs_apiKey(), requestTestData.getSource2(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_VALID_SOURCE_SMB", requestTestData.getSmb_apiKey(), requestTestData.getSource3(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_VALID_SOURCE_MBE", requestTestData.getMbe_apiKey(), requestTestData.getSource4(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_VALID_PRINCIPALID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_VALID_REDIRECTURL", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getRedirectURL()},
                {"WITH_TERMINAL_STATE_DECLINED_USER",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID_declinedState(), requestTestData.getRedirectURL()},

        };
    }
    @SneakyThrows
    @DataProvider(name = "startLinkInvalidPayloadData")
    public final static Object[][] startLinkInvalidPayloadData() {
        return new Object[][]{
                {"INVALID_APIKEY", requestTestData.getLp_apiKey() + "--", requestTestData.getSource1(), requestTestData.getPrincipalID(), requestTestData.getRedirectURL()},
                {"INVALID_SOURCE", requestTestData.getLp_apiKey(), INVALID_SOURCE, requestTestData.getPrincipalID(), requestTestData.getRedirectURL()},
                {"EMPTY_PRINCIPALID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), EMPTY_USER_UUID, requestTestData.getRedirectURL()},
                {"EMPTY_REDIRECTURL", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(), ""},
                // {"WITH_TERMINAL_STATE_USER",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(), requestTestData.getRedirectURL()}
                //  {"WITH_TERMINAL_STATE_APPROVED_USER",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID_declinedState(), requestTestData.getRedirectURL()}

        };

    }
    @SneakyThrows
    @DataProvider(name = "digilockerResultsTestData")
    public final static Object[][] digilockerResultsTestData() {
        return new Object[][]{
                {"WITH_VALID_INPUTS",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id_approvedState()},
                {"INVALID_APIKEY",requestTestData.getLp_apiKey()+"--", requestTestData.getSource1(), requestTestData.getTxn_id_approvedState()},
                {"INVALID_SOURCE", requestTestData.getLp_apiKey(), INVALID_SOURCE, requestTestData.getTxn_id_approvedState()},
                {"VALID_SOURCE_LP", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id_approvedState()},
                {"VALID_SOURCE_PS", requestTestData.getPs_apiKey(), requestTestData.getSource2(), requestTestData.getPs_txnId()},
                {"VALID_SOURCE_SMB", requestTestData.getSmb_apiKey(), requestTestData.getSource3(), requestTestData.getSmb_txnId()},
                {"INVALID_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id()+"-"},
                {"NONEXIST_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id()+"xx"},
                {"EMPTY_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), EMPTY_USER_UUID},
                {"AFTER_DIGILOCKER_JOURNEY_STARTED", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id_startedState()},
                {"AFTER_DIGILOCKER_JOURNEY_APPROVED", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id_approvedState()},
                {"AFTER_DIGILOCKER_JOURNEY_DECLINED", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id_autoDeclinedState()},
        };
    }
    @SneakyThrows
    @DataProvider(name = "digilockerStatusTestData")
    public final static Object[][] digilockerStatusTestData() {
        return new Object[][]{
                {"WITH_VALID_INPUTS", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getTxn_id_startedState()},
                {"VALID_SOURCE_LP", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getTxn_id_startedState()},
                {"VALID_SOURCE_PS", requestTestData.getPs_apiKey(), requestTestData.getSource2(), requestTestData.getPs_principalId(),requestTestData.getPs_txnId()},
                {"VALID_SOURCE_SMB", requestTestData.getSmb_apiKey(), requestTestData.getSource3(), requestTestData.getSmb_principalId(),requestTestData.getSmb_txnId()},
                {"INVALID_APIKEY", requestTestData.getLp_apiKey()+")(^%%", requestTestData.getSource1(), requestTestData.getTxn_id(),requestTestData.getTxn_id_startedState()},
                {"INVALID_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getTxn_id()+"-"},
                {"INVALID_SOURCE", requestTestData.getLp_apiKey(), requestTestData.getSource1()+"tsw", requestTestData.getPrincipalID(),requestTestData.getTxn_id_startedState()},
                {"WITH_APPROVED_TRANSACTIONID",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getPrincipalID_approvedState()},
                // {"WITH_DECLINED_TRANSACTIONID",requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getPrincipalID(),requestTestData.getPrincipalID_declinedState()},
        };
    }
    @SneakyThrows
    @DataProvider(name = "NonRequiredParamTestData")
    public final static Object[][] NonRequiredParamTestData() {
        return new Object[][]{
                {"EMPTY_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), ""},
                {"NONEXIST_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), "xxx"+requestTestData.getTxn_id()},
                {"NONEXIST_PRINCIPALID",requestTestData.getLp_apiKey(), requestTestData.getSource1(), "0"+requestTestData.getPrincipalID()},
                {"EMPTY_PRINCIPALID",requestTestData.getLp_apiKey(), requestTestData.getSource1(), ""},
                {"WITH_ONLY_PRINCIPALID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), "1"},
                {"WITH_ONLY_TRANSACTIONID", requestTestData.getLp_apiKey(), requestTestData.getSource1(), requestTestData.getTxn_id_startedState()},

        };
    }
}