package platform.userplatform;

import io.qameta.allure.Description;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.testng.annotations.Test;
import pojos.ams.OauthTokenResponse;
import pojos.ams.oauthTokenWrongOtpResponse;
import pojos.ams.validateUserPojo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AlphaNumericOtpTestTwo extends UserPlatformSupportData {

    public AlphaNumericOtpTestTwo() throws Exception {

    }

    String OTP_VALUE;
    String OTP_STATUS;

    Timestamp SENT_TIME;
    Timestamp EXPIRY_TIME;
    Timestamp OTP_CREATED_AT;
    Timestamp OTP_UPDATED_AT;

    @SneakyThrows
    @Test(priority = 1)
    @Description("The test will verify numeric BNPL otp flow for existing user")
    public void FourDigitNumericOtpTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitNumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        OauthTokenResponse response=oauthTokenResponse(otp,CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        String uuid2=getuuidFromUserDb("um_uuid",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE );
        String accessTokenFromDb=getOauthToken("token_value",uuid,CLIENT_ID_ONE);
        String regreshToken=getrefreshToken("refresh_token",uuid,CLIENT_ID_ONE);
        Assert.assertEquals(response.access_token,accessTokenFromDb);
        Assert.assertEquals(response.refresh_token,regreshToken);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }
    @SneakyThrows
    @Test(priority = 2)
    @Description("The test will verify alphanumeric BNPL otp flow for existing user")
    public void FourDigitAlphaNumericOtpTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        OauthTokenResponse response=oauthTokenResponse(otp,CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        //TODO DB validations
        String uuid2=getuuidFromUserDb("um_uuid",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE );
        String accessTokenFromDb=getOauthToken("token_value",uuid,CLIENT_ID_ONE);
        String refreshToken=getrefreshToken("refresh_token",uuid,CLIENT_ID_ONE);
        Assert.assertEquals(response.access_token,accessTokenFromDb);
        Assert.assertEquals(response.refresh_token,refreshToken);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }
    @SneakyThrows
    @Test(priority = 3)
    @Description("The test will verify numeric BNPL otp flow for new user")
    public void NewUserFourDigitAlphaNumericOtpTest(){
        purgeUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW1);
        deleteUserDataFromUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW1);
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,NEW_USER_MOBILE_NUMBER_OTP_FLOW1);
        String uuid2=getuuidFromUserDb("um_uuid",NEW_USER_MOBILE_NUMBER_OTP_FLOW1 );
        OauthTokenResponse response=verifyMobileSignInResponse(uuid,otp,CLIENT_ID_ONE,CLIENT_REF_ID);
        String accessTokenFromDb=getOauthToken("token_value",uuid,CLIENT_ID_ONE);
        String refreshToken=getrefreshToken("refresh_token",uuid,CLIENT_ID_ONE);
        Assert.assertEquals(response.access_token,accessTokenFromDb);
        Assert.assertEquals(response.refresh_token,refreshToken);
        sendOTP(NEW_USER_MOBILE_NUMBER_OTP_FLOW1);
    }
    @SneakyThrows
    @Test(priority = 4)
    @Description("The test will verify Alphanumeric BNPL otp flow for new user")
    public void NewUserFourDigitNumericOtpTest(){
        purgeUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW2);
        deleteUserDataFromUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW2);
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitNumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,NEW_USER_MOBILE_NUMBER_OTP_FLOW2);
        String uuid2=getuuidFromUserDb("um_uuid",NEW_USER_MOBILE_NUMBER_OTP_FLOW2 );
        OauthTokenResponse response=verifyMobileSignInResponse(uuid,otp,CLIENT_ID_ONE,CLIENT_REF_ID);
        String accessTokenFromDb=getOauthToken("token_value",uuid,CLIENT_ID_ONE);
        String refreshToken=getrefreshToken("refresh_token",uuid,CLIENT_ID_ONE);
        Assert.assertEquals(response.access_token,accessTokenFromDb);
        Assert.assertEquals(response.refresh_token,refreshToken);
        sendOTP(NEW_USER_MOBILE_NUMBER_OTP_FLOW2);
    }
    @SneakyThrows
    @Test(priority = 5)
    @Description("The test will verify differnt otp for same clientid and different amount within otp expiry time")
    public void sameClientIdDifferntClientRefIdTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        String CLIENT_REF_ID_NEW=clientRefIdGenerator();
        String otp_NEW=getFourDigitAlphanumericOTP(CLIENT_REF_ID_NEW,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        Assert.assertNotSame(otp,otp_NEW);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }

    @SneakyThrows
    @Test(priority = 6)
    @Description("The test will verify differnt otp for differnt clientid but same clientRefId")
    public void DifferntClientIdSameClientRefIDTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        String otp_NEW=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_TWO,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        Assert.assertNotSame(otp,otp_NEW);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }
    @SneakyThrows
    @Test(priority = 7)
    @Description("The test will verify with each wrong otp attempt request increasing")
    public void verifyingWrongAttemptTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        oauthTokenWrongOtpResponse response= oauthTokenWithWrongOtpResponse("1234",CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        int status=response.status;
        String errorMsg=response.error;
        String message=response.message;
        Assert.assertEquals(status,401);
        Assert.assertTrue(message.contains("Invalid verification code"));
        Assert.assertEquals(errorMsg,"Unauthorized");
        int otpRequestAttempt=getOtpRequestAttemptedFromUserDb("otp_request_attempts",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        Assert.assertEquals(1,otpRequestAttempt);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }
    @SneakyThrows
    @Test(priority = 8)
    @Description("The test will verify with each wrong otp attempt request increasing")
    public void verifyingOTPResetAftersuccessfulValidationTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        oauthTokenWrongOtpResponse response= oauthTokenWithWrongOtpResponse("1234",CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        int status=response.status;
        String errorMsg=response.error;
        String message=response.message;
        Assert.assertEquals(status,401);
        Assert.assertTrue(message.contains("Invalid verification code"));
        Assert.assertEquals(errorMsg,"Unauthorized");
        int otpRequestAttempt=getOtpRequestAttemptedFromUserDb("otp_request_attempts",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        Assert.assertTrue(otpRequestAttempt>0);
        oauthTokenResponse(otp,CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        otpRequestAttempt=getOtpRequestAttemptedFromUserDb("otp_request_attempts",EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        Assert.assertEquals(0,otpRequestAttempt);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }
    @SneakyThrows
    @Test(priority = 9)
    @Description("The test will verify if user exhausted his/her otp attempt then user will blocked for 15 min.")
    public void verifyUserBlockedInCaseOtpAttemptExhausted(){
        int i=0;
        validateUserPojo findOrCreateUserResponse=new validateUserPojo();
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("clientId", amsTestData.clientId);
        queryParamDetails.put("clientSecret", amsTestData.clientSecret);
        queryParamDetails.put("mobile", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        queryParamDetails.put("lazypayLogin", "true");
        queryParamDetails.put("source", SOURCE);
        queryParamDetails.put("policyId", SAURON_APP_LOGIN_NUMERIC_V1);
        while(i<=21){
            findOrCreateUserResponse = createuser.post(queryParamDetails, headerDetails, "");
            i++;
        }
        String status=findOrCreateUserResponse.status;
        String error=findOrCreateUserResponse.error;
        String message=findOrCreateUserResponse.message;
        Assert.assertEquals("failed to check to status","403",status);
        Assert.assertEquals("failed to check to error","Forbidden",error);
        Assert.assertTrue("failed to check to message",message.contains("User can request otp again after"));
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
    }

    @SneakyThrows
    @Test(priority = 11)
    @Description("The test will verify the properties of alphanumeric otp.")
    public void alphanumericBnplOtpProperties(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        String otp=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_2);
        Assert.assertTrue(amsTestData.isOtpAlphaNumeric(otp));
        Assert.assertTrue(amsTestData.isOtpOfFourDigits(otp));
        Assert.assertTrue(amsTestData.isOtpContainsillegalCharacter(otp));
        Assert.assertFalse(amsTestData.isOtpContainsSpecialCharacter(otp));
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_2);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_2);
    }
    @SneakyThrows
    @Test(priority = 12)
    @Description("The test will verify Bad request if we are trying to generate alphanumeric otp for login")
    public void alphanumericotpForLogin(){
        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();
        headerDetails.put("Content-Type", "application/json");
        headerDetails.put("Accept", "application/json");
        queryParamDetails.put("clientId", amsTestData.clientId);
        queryParamDetails.put("clientSecret", amsTestData.clientSecret);
        queryParamDetails.put("mobile", EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        queryParamDetails.put("lazypayLogin", "true");
        queryParamDetails.put("source", SOURCE);
        queryParamDetails.put("otpFormat","ALPHA_NUMERIC");
        queryParamDetails.put("policyId", SECUREAPP_WEBAPP_TRANSACTIONS_ALPHANUMERIC_V1);
        validateUserPojo findOrCreateUserResponse=createuser.post(queryParamDetails, headerDetails, "");
        String status=findOrCreateUserResponse.status;
        String error=findOrCreateUserResponse.error;
        String message=findOrCreateUserResponse.message;
        Assert.assertEquals("400",status);
        Assert.assertEquals("Bad Request",error);
        Assert.assertEquals("Please provide valid otp format for login",message);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE);

    }
    @SneakyThrows
    @Test(priority = 13)
    @Description("The test will verify the difference between OTP sent time and OTP expiry for bnpl otp time is 3min.")
    public void otpExpiryTimeValidation(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        OTP_VALUE=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        EXPIRY_TIME=getOtpExpiryTime("expire_at",OtpId);
        SENT_TIME=getOtpSentTime("created_at",OtpId);
        long OTP_EXPIRY_TIME=(EXPIRY_TIME.getTime()-SENT_TIME.getTime());
        long minutes=(OTP_EXPIRY_TIME/1000)/60;
        Assert.assertEquals(3,minutes);
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
    }
    
    @SneakyThrows
    @Test(priority = 14)
    @Description("The test will verify the Otp status for both generated and validated state for existing user")
    public void numericotpStatusValidation(){
        String CLIENT_REF_ID = clientRefIdGenerator();
        OTP_VALUE = getFourDigitNumericOTP(CLIENT_REF_ID, CLIENT_ID_ONE, EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("GENERATED",OTP_STATUS);
        OauthTokenResponse response=oauthTokenResponse(OTP_VALUE,CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("VALIDATED",OTP_STATUS);

    }
    @SneakyThrows
    @Test(priority = 15)
    @Description("The test will verify the alpha numeric Otp status for both generated and validated state for existing user")
    public void alphaNumericotpStatusValidation(){
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        String CLIENT_REF_ID=clientRefIdGenerator();
        OTP_VALUE=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        Thread.sleep(3000);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("GENERATED",OTP_STATUS);
        OauthTokenResponse response=oauthTokenResponse(OTP_VALUE,CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        Thread.sleep(3000);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("VALIDATED",OTP_STATUS);

    }

    @SneakyThrows
    @Test(priority = 20)
    @Description("The test will verify the Otp status for both generated and validated state for new user user")
    public void numericotpStatusValidationForNewUser(){
        purgeUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW3);
        deleteUserDataFromUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW3);
        String CLIENT_REF_ID=clientRefIdGenerator();
        OTP_VALUE=getFourDigitNumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,NEW_USER_MOBILE_NUMBER_OTP_FLOW3);
        Thread.sleep(3000);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("GENERATED",OTP_STATUS);
        String uuid2=getuuidFromUserDb("um_uuid",NEW_USER_MOBILE_NUMBER_OTP_FLOW3 );
        OauthTokenResponse response=verifyMobileSignInResponse(uuid,OTP_VALUE,CLIENT_ID_ONE,CLIENT_REF_ID);
        Thread.sleep(3000);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("VALIDATED",OTP_STATUS);
        sendOTP(NEW_USER_MOBILE_NUMBER_OTP_FLOW3);
    }
    @SneakyThrows
    @Test(priority = 21)
    @Description("The test will verify the alpha numeric Otp status for both generated and validated state for new user user")
    public void alphanumericotpStatusValidationForNewUser(){
        purgeUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW4);
        deleteUserDataFromUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW4);
        String CLIENT_REF_ID=clientRefIdGenerator();
        OTP_VALUE=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,NEW_USER_MOBILE_NUMBER_OTP_FLOW4);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("GENERATED",OTP_STATUS);
        String uuid2=getuuidFromUserDb("um_uuid",NEW_USER_MOBILE_NUMBER_OTP_FLOW4 );
        verifyMobileSignInResponse(uuid,OTP_VALUE,CLIENT_ID_ONE,CLIENT_REF_ID);
        OTP_STATUS=getOtpStatus("otp_status",OtpId);
        Assert.assertEquals("VALIDATED",OTP_STATUS);
        sendOTP(NEW_USER_MOBILE_NUMBER_OTP_FLOW4);

    }
    @SneakyThrows
    @Test(priority = 22)
    @Description("The test will verify the created at and updated at time are different once the otp is validated for new user.")
    public void otpCreatedAtAndUpdatedAtDateValidationNewUser(){
        purgeUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW5);
        deleteUserDataFromUser(NEW_USER_MOBILE_NUMBER_OTP_FLOW5);
        String CLIENT_REF_ID=clientRefIdGenerator();
        OTP_VALUE=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,NEW_USER_MOBILE_NUMBER_OTP_FLOW5);
        OTP_CREATED_AT=getOtpCreatedAt("created_at",OtpId);
        OTP_UPDATED_AT=getOtpUpdatedAt("updated_at",OtpId);
        Assert.assertEquals(OTP_CREATED_AT,OTP_UPDATED_AT);
        String uuid2=getuuidFromUserDb("um_uuid",NEW_USER_MOBILE_NUMBER_OTP_FLOW5 );
        Thread.sleep(10000);
        OauthTokenResponse response=verifyMobileSignInResponse(uuid,OTP_VALUE,CLIENT_ID_ONE,CLIENT_REF_ID);
        OTP_CREATED_AT=getOtpCreatedAt("created_at",OtpId);
        OTP_UPDATED_AT=getOtpUpdatedAt("updated_at",OtpId);
        Assert.assertTrue("failed to updated date, created time is "+ OTP_CREATED_AT + "updated time is " + OTP_UPDATED_AT,!OTP_CREATED_AT.equals(OTP_UPDATED_AT) );
        sendOTP(NEW_USER_MOBILE_NUMBER_OTP_FLOW5);
    }
    @SneakyThrows
    @Test(priority = 23)
    @Description("The test will verify the created at and updated at time are different once the otp is validated for existing user.")
    public void otpCreatedAtAndUpdatedAtDateValidationExistingUser(){
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        String CLIENT_REF_ID=clientRefIdGenerator();
        OTP_VALUE=getFourDigitAlphanumericOTP(CLIENT_REF_ID,CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        OTP_CREATED_AT=getOtpCreatedAt("created_at",OtpId);
        OTP_UPDATED_AT=getOtpUpdatedAt("updated_at",OtpId);
        Assert.assertEquals(OTP_CREATED_AT,OTP_UPDATED_AT);
        Thread.sleep(13000);
        OauthTokenResponse response=oauthTokenResponse(OTP_VALUE,CLIENT_ID_ONE,CLIENT_REF_ID,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        OTP_CREATED_AT=getOtpCreatedAt("created_at",OtpId);
        Thread.sleep(3000);
        OTP_UPDATED_AT=getOtpUpdatedAt("updated_at",OtpId);
        Thread.sleep(3000);
        Assert.assertTrue("failed to updated date, created time is "+ OTP_CREATED_AT + "updated time is " + OTP_UPDATED_AT,!OTP_CREATED_AT.equals(OTP_UPDATED_AT) );
        updateOtpRequestedAttempt(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        updateLoginAttempts(EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
    }
    }


