package platform.otp;

import api.otpservice.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dbUtils.ams_MySQL_DBAccessObject;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.DataProvider;
import pojos.otpservice.*;
import util.ReadProperties;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class otpserviceutilities {
    public otpserviceutilities() throws Exception {
    }
    public static final String SBOX_CONFIG_PROPERTIES_PATH="src/main/resources/config_sbox.properties";
    /**
     * OTP table column details
     *
     * */
    private static final String GET_POLICY_ID_FROM_OTP = "SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_TENANT_ID= "SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_OTP_VALUE="SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_OTP_CREATION_TIME="SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_OTP_UPDATION_TIME="SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_NUMBER_OF_VALIDATION_ATTEMPTS="SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_NUMBER_OF_RESEND_ATTEMPTS="SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String GET_OTP_STATUS="SELECT # FROM otpdb.otp WHERE otp_id='$'";
    private static final String DELETE_OTP_ID="DELETE FROM otpdb.otp WHERE otp_id='$'";
    /**
     * Policy table column details
     *
     * */
    private static final String GET_TENANT_ID_POLICY="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_POLICY_NAME="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_POLICY_CREATION_TIME="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_OTP_TYPE_FROM_POLICY="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_OTP_CONFIG_FROM_POLICY="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_OTP_LENGTH_FROM_POLICY="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_MAX_VALIDATION_ATTEMPTS="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_MAX_RESEND_ATTEMPTS="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String GET_OTP_EXPIRY_TIME="SELECT # FROM otpdb.policy WHERE policy_id='$'";
    private static final String DELETE_POLICY_ID="DELETE FROM otpdb.policy WHERE policy_id='$'";
    createPolicy createpolicy=new createPolicy();
    getPolicy getpolicy=new getPolicy();
    deletePolicy deletepolicy=new deletePolicy();
    generateOtp generateotp=new generateOtp();
    validateOtp validateotp=new validateOtp();
    resendOtp resendotp=new resendOtp();
    createPolicyRequestPojo createpolicyrequest=new createPolicyRequestPojo();
    OtpConfig otpconfig=new OtpConfig();
    generateOtpRequestPojo generateotprequest=new generateOtpRequestPojo();
    validateOtpRequestPojo validateotprequest=new validateOtpRequestPojo();
    resendOtpRequestPojo resendotprequest=new resendOtpRequestPojo();
    Faker faker=new Faker();
    String POLICY_NAME="POLICY_TEST"+faker.name().toString();



    /**
     * OTP table methods
     *
     * */
    @SneakyThrows
    @Step
    public static int getPolicyIdFromOtpTable(String policyId, String otpId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_POLICY_ID_FROM_OTP.replace("#", policyId).replace("$", otpId));
        while (rs.next())
            s = rs.getInt(policyId);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getTenantIdFromOtpTable(String tenantId, String otpId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_TENANT_ID.replace("#", tenantId).replace("$", otpId));
        while (rs.next())
            s = rs.getInt(tenantId);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getNumberOfValidationAttemptFromOtpTable(String validationAttempt, String otpId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_NUMBER_OF_VALIDATION_ATTEMPTS.replace("#", validationAttempt).replace("$", otpId));
        while (rs.next())
            s = rs.getInt(validationAttempt);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getNumberOfResendAttemptFromOtpTable(String resendAttempt, String otpId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_NUMBER_OF_RESEND_ATTEMPTS.replace("#", resendAttempt).replace("$", otpId));
        while (rs.next())
            s = rs.getInt(resendAttempt);
        return s;
    }
    @SneakyThrows
    @Step
    public static String getOtpValueFromOtpTable(String otp, String otpId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_VALUE.replace("#", otp).replace("$", otpId));

        while (rs.next())
            s = rs.getString(otp);

        return s;

    }
    @SneakyThrows
    @Step
    public static Timestamp getOtpCreationTime(String otpSentTime, String otpId) {

        Timestamp s = null;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_CREATION_TIME.replace("#", otpSentTime).replace("$", otpId));
        while (rs.next())
            s = rs.getTimestamp(otpSentTime);
        return s;
    }
    @SneakyThrows
    @Step
    public static Timestamp getOtpUpdationTime(String otpupdatetime, String otpId) {

        Timestamp s = null;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_UPDATION_TIME.replace("#", otpupdatetime).replace("$", otpId));
        while (rs.next())
            s = rs.getTimestamp(otpupdatetime);
        return s;
    }
    @SneakyThrows
    @Step
    public static String getOtpStatusFromOtpTable(String status, String otpId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_STATUS.replace("#", status).replace("$", otpId));

        while (rs.next())
            s = rs.getString(status);

        return s;

    }
    @SneakyThrows
    @Step
    public static void deleteOtpIdFromOtpTable(String otpId) {
        try {
            ams_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_OTP_ID.replace("$", otpId));
        } catch (Exception e) {
            log.debug("ivr entry is already deleted in the Table" + e);
        }
    }
    /**
     * Policy Table methods
     *
     * */
    @SneakyThrows
    @Step
    public static int getOtpExpiryTimeFromPolicyTable(String otpExpiryTime, String policyId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_EXPIRY_TIME.replace("#", otpExpiryTime).replace("$", policyId));
        while (rs.next())
            s = rs.getInt(otpExpiryTime);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getTenantIdFromPolicyTable(String tenantId, String policyId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_TENANT_ID_POLICY.replace("#", tenantId).replace("$", policyId));
        while (rs.next())
            s = rs.getInt(tenantId);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getNumberOfValidationAttemptFromPolicyTable(String validationAttempt, String policyId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_MAX_VALIDATION_ATTEMPTS.replace("#", validationAttempt).replace("$", policyId));
        while (rs.next())
            s = rs.getInt(validationAttempt);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getNumberOfResendAttemptFromPolicyTable(String resendAttempt, String policyId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_MAX_RESEND_ATTEMPTS.replace("#", resendAttempt).replace("$", policyId));
        while (rs.next())
            s = rs.getInt(resendAttempt);
        return s;
    }
    @SneakyThrows
    @Step
    public static String getPolicyNameTable(String policyName, String policyId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_POLICY_NAME.replace("#", policyName).replace("$", policyId));
        while (rs.next())
            s = rs.getString(policyName);
        return s;
    }
    @SneakyThrows
    @Step
    public static Timestamp getPolicyCreationTime(String policyCreationTime, String policyId) {

        Timestamp s = null;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_POLICY_CREATION_TIME.replace("#", policyCreationTime).replace("$", policyId));
        while (rs.next())
            s = rs.getTimestamp(policyCreationTime);
        return s;
    }
    @SneakyThrows
    @Step
    public static int getOtpLengthFromPolicyTable(String otpLength, String policyId) {

        int s = 0;
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_LENGTH_FROM_POLICY.replace("#", otpLength).replace("$", policyId));
        while (rs.next())
            s = rs.getInt(otpLength);
        return s;
    }
    @SneakyThrows
    @Step
    public static String getOtpTypeFromPolicyTable(String otpType, String policyId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_TYPE_FROM_POLICY.replace("#", otpType).replace("$", policyId));
        while (rs.next())
            s = rs.getString(otpType);
        return s;

    }
    @SneakyThrows
    @Step
    public static void deletePolicyIdIdFromOtpTable(String policyId) {
        try {
            ams_MySQL_DBAccessObject.deleteOnMySqlDb(DELETE_POLICY_ID.replace("$", policyId));
        } catch (Exception e) {
            log.debug("policyId is already deleted in the Table" + e);
        }
    }
    @SneakyThrows
    @Step
    public static String getOtpConfigFromPolicyTable(String otpConfig, String policyId) {

        String s = "";
        ResultSet rs = ams_MySQL_DBAccessObject.selectFromMySqlDb(GET_OTP_CONFIG_FROM_POLICY.replace("#", otpConfig).replace("$", policyId));
        while (rs.next())
            s = rs.getString(otpConfig);
        return s;
    }

    @SneakyThrows
    @Step
    public createPolicyResponsePojo createPolicy(String otpType, int otpLength, int mindigit,int minalphabet,
                                                    String excludedigit, String excludealphabet, int expiryTime,
                                                 String policyName,int maxValidationAttempt,int maxResendAttempt)
    {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        otpconfig.setOtpType(otpType);
        otpconfig.setOtpLength(otpLength);
        otpconfig.setMinimumNumberOfDigits(mindigit);
        otpconfig.setMinimumNumberOfAlphabets(minalphabet);
        otpconfig.setExcludeDigits(excludedigit);
        otpconfig.setExcludeAlphabets(excludealphabet);
        otpconfig.setOtpExpiryInSeconds(expiryTime);
        createpolicyrequest.setPolicyName(policyName);
        createpolicyrequest.setTenantId("TESTTEST");
        createpolicyrequest.setMaxResendAttempts(maxResendAttempt);
        createpolicyrequest.setMaxValidationAttempts(maxValidationAttempt);
        createpolicyrequest.setOtpConfig(otpconfig);
        ObjectMapper objectmapper=new ObjectMapper();
        String JsonBody=objectmapper.writeValueAsString(createpolicyrequest);
        createPolicyResponsePojo createpolicyresponse=createpolicy.post(queryParamDetails,headerDetails,JsonBody);
        return createpolicyresponse;
    }
    @SneakyThrows
    @Step
    public getPolicyResponsePojo getPolicy(int policyId)
    {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("policyId",policyId);
        getPolicyResponsePojo getPolicyresponse=getpolicy.get(queryParamDetails,headerDetails);
        return getPolicyresponse;
    }
    @SneakyThrows
    @Step
    public deletePolicyResponsePojo deletePolicy(int policyId)
    {
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("policyId",policyId);
        deletePolicyResponsePojo deletePolicyresponse=deletepolicy.delete(queryParamDetails,headerDetails);
        return deletePolicyresponse;
    }
    @SneakyThrows
    @Step
    public generateOtpResponsePojo generateotp(int policyId,String tenantId){
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        generateotprequest.setPolicyId(policyId);
        generateotprequest.setTenantId(tenantId);
        ObjectMapper objectmapper=new ObjectMapper();
        String JsonBody=objectmapper.writeValueAsString(generateotprequest);
        generateOtpResponsePojo generateotpresponse= generateotp.post(queryParamDetails,headerDetails,JsonBody);
        return generateotpresponse;
    }
    @SneakyThrows
    @Step
    public validateOtpResponsePojo validateotp(String otpId,String otpValue,String tenantId,String resendDelayInSeconds){
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        validateotprequest.setOtpId(otpId);
        validateotprequest.setOtpValue(otpValue);
        validateotprequest.setTenantId(tenantId);
        ObjectMapper objectmapper=new ObjectMapper();
        String JsonBody=objectmapper.writeValueAsString(validateotprequest);
        validateOtpResponsePojo validateotpresponse= validateotp.post(queryParamDetails,headerDetails,JsonBody);
        return validateotpresponse;
    }
    @SneakyThrows
    @Step
    public resendOtpResponsePojo resendotp(String otpId,String tenantId){
        HashMap<String, String> headerDetails = new HashMap<>();
        HashMap<String, Object> queryParamDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        resendotprequest.setOtpId(otpId);
        resendotprequest.setTenantId(tenantId);
        ObjectMapper objectmapper=new ObjectMapper();
        String JsonBody=objectmapper.writeValueAsString(resendotprequest);
        resendOtpResponsePojo resendotpresponse= resendotp.post(queryParamDetails,headerDetails,JsonBody);
        return resendotpresponse;
    }
    /**
     * TestCaseName, policyId, tenantId
     * @return
     */
    @DataProvider(name = "otpGenerationData")
    public Object[][] otpGenerationData() {
        return new Object[][]{
                // TestCaseName, policyId, tenantId ,otpLength
                {"SAURON_APP-LOGIN-NUMERIC_V1", 1, "ec35b493",6},
                {"SECUREAPP-WEBAPP_LOGIN-NUMERIC_V1", 2 , "ec35b493",6},
                {"SECUREAPP-WEBAPP_TRANSACTIONS-NUMERIC_V1", 3, "ec35b493",4},
                {"SECUREAPP-WEBAPP_TRANSACTIONS-ALPHANUMERIC_V1", 4, "ec35b493",4},
                {"BILLPAY_TRANSACTIONS_V1", 5, "ec35b493",6},
                {"SECUREAPP-WEBAPP_ACCOUNT-LINK_V1", 6, "ec35b493",4},

        };
    }
    @DataProvider(name = "otpGenerationDataNegativeCase")
    public Object[][] otpGenerationDataNegativeCase() {
        return new Object[][]{
                // TestCaseName, policyId, tenantId ,internalCode,internalMessage,endUserMessage
                {"WRONG_POLICY_ID", 1000, "ec35b493","OTP-1006","Policy not found!","Some error happened, Please try again"},
                {"WRONG_TENANT_ID", 2 , "ec35b493546","OTP-1002","Invalid field values, Please update and try again","Invalid field values, Please update and try again"},
        };
    }
    /**
     * TestCaseName,otpType , otpLength ,mindigit,minalphabet,excludedigit,excludealphabet,expiryTime,
     *               policyName,Success-message,internalCode,InternalMessage,endUserMessage
     * @return
     */
    @DataProvider(name = "policyCreationData")
    public Object[][] policyCreationData() {
        return new Object[][]{
                // TestCaseName,otpType , otpLength ,mindigit,minalphabet,excludedigit,excludealphabet,expiryTime,
                // policyName,Success-message,internalCode,InternalMessage,endUserMessage
                {"CREATE_POLICY_NUMERIC_OTP", "NUMERIC",6,3,null,"123","OIoil",300,POLICY_NAME,3,3,"Successfully created policy."," "," "," "},
                {"CREATE_POLICY_ALPHANUMERIC_OTP","ALPHANUMERIC",6,3,3,"123","OIoil",300,POLICY_NAME,3,3,"Successfully created policy."," "," "," "},
                {"CREATE_POLICY_NUMERIC_OTP_WITHOUT_MIN_DIGIT","NUMERIC",6,null,null,"123","OIoil",300,POLICY_NAME,3,3," ",
                        "OTP-1002","Invalid field values, Please update and try again","Invalid field values, Please update and try again"},
                {"CREATE_POLICY_ALPHANUMERIC_OTP_WITHOUT_MIN_ALPHABET","ALPHANUMERIC",6,3,null,"123","OIoil",300,POLICY_NAME,3,3," ",
                        "OTP-1002","Invalid field values, Please update and try again","Invalid field values, Please update and try again"},
                {"CREATE_NUMERIC_OTP_WHERE_MIN_DIGIT_GREATER_THAN_OTP_LENGTH","NUMERIC",6,8,null,"123","OIoil",300,POLICY_NAME,3,3," ",
                        "OTP-1002","Invalid number of digits for numeric OTP","Number of digits should be not be greater than otp length in a numeric OTP."},
                {"CREATE_ALPHA_NUMERIC_OTP_WHERE_MIN_DIGIT+ALPHABET_GREATER_THAN_OTP_LENGTH","ALPHANUMERIC",6,3,5,"123","OIoil",300,POLICY_NAME,3,3," ",
                        "OTP-1002","Invalid number of digits/alphabets for alphaNumeric OTP","Sum of minimum number of alphabets and digits should not be greater than otp_length for AlphaNumeric OTP."},
                {"CREATE_POLICY_WHERE_MAX_VALIDATION_ATTEMPT_ZERO","NUMERIC",6,3,null,"123","OIoil",300,POLICY_NAME,0,3," ",
                        "OTP-1002","Invalid field values, Please update and try again","Invalid field values, Please update and try again"},
                {"CREATE_POLICY_WHERE_MAX_RESEND_ATTEMPT_ZERO","NUMERIC",6,3,null,"123","OIoil",300,POLICY_NAME,3,0," ",
                        "OTP-1002","Invalid field values, Please update and try again","Invalid field values, Please update and try again"},
                {"CREATE_POLICY_WITH_DUPLICATE_NAME","NUMERIC",6,3,null,"123","OIoil",300,"TEST_AUTOMATION_POLICY",3,3," ",
                        "OTP-1002","Policy name already exists","Policy name already exists, Please provide a different policy name."},
        };
    }

  @SneakyThrows
  @Step
  public static String getDecryptedOtp(String encryptedInput, String secretKey ) {
      Properties prop = ReadProperties.readPropertyFile(SBOX_CONFIG_PROPERTIES_PATH);
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), prop.getProperty("algorithm"));
      IvParameterSpec ivSpec = new IvParameterSpec(prop.getProperty("initializationVector").getBytes(StandardCharsets.UTF_8));
      Cipher cipher = Cipher.getInstance(prop.getProperty("algorithmMode"));
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
      byte[] encryptedBytes = Base64.decodeBase64(encryptedInput);
      byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
      return new String(decryptedBytes);
  }


    public boolean isOtpContainsExcludeCharacter(String s,String excludeCharacters){
        char[] invalidChars=excludeCharacters.toCharArray();
        if(StringUtils.containsNone(s,invalidChars))
            return true;
        else
            return false;
    }
    public boolean isOtpContatinSpecialCharater(String str){
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
        Matcher match = pattern.matcher(str);
        boolean val;
        if(val = match.find())
           return true;
        else
            return false;
    }

}
