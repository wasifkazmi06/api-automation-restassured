package platform.userplatform;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.PlatformSupportData;
import platform.otp.otpserviceutilities;
import pojos.ams.bbpsCallBackResponse;
import pojos.ams.bbpsInitiateOtpResponse;
import pojos.ams.bbpsValidateOtpResponse;
import pojos.otpservice.resendOtpResponsePojo;

import java.sql.Timestamp;

public class bbpsOtpTest extends UserPlatformSupportData {

    public bbpsOtpTest() throws Exception{
    }
    String OTP_VALUE;
    String OTP_STATUS;
    Timestamp OTP_CREATED_AT;
    Timestamp OTP_UPDATED_AT;
    String IVR_ID;
    int IVR_REQUEST_ID;
    otpserviceutilities otpservice=new otpserviceutilities();

    @SneakyThrows
    @Test(priority = 1)
    @Description("The test will verify generation of autoOtp and will validate api response with db entries")
    public void bbpsOtpTest_autoOtpGenerationTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        Assert.assertTrue(response.getRequestId().matches("^[a-zA-Z0-9]*$"));
        String mobile=response.getMobile();
        Assert.assertEquals(mobile,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        String expiryIn=response.getExpiresIn();
        Assert.assertEquals(expiryIn,"6m:00s");
    }
    @SneakyThrows
    @Test(priority = 2)
    @Description("The test will verify validation of autoOtp and will validate api response with db entries")
    public void bbpsOtpTest_autoOtpValidationTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);

        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        resendOtpResponsePojo resendotp= otpservice.resendotp(requestId,TENANT_ID);
        OTP_VALUE=resendotp.getOtpValue();
        bbpsValidateOtpResponse validateOtpResponse=bbpsvalidateotp(CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO
                ,CLIENT_REF_ID,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,OTP_VALUE,requestId);
        String requestIdValidateOtp=validateOtpResponse.getRequestId();
        String authType=validateOtpResponse.getAuthType();
        String status=validateOtpResponse.getStatus();
        Assert.assertEquals(mobile,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO);
        Assert.assertEquals(requestId,requestIdValidateOtp,"RequestId from initiateOtp and ValidateOtp doesn't match");
        Assert.assertEquals(authType,AUTO_OTP_SOURCE);
        Assert.assertEquals(status,"SUCCESS");
    }
    @SneakyThrows
    @Test(priority = 3)
    @Description("Here will validate scenarios with invalid mobile number and invalid otp-source for initiate otp api")
    public void bbpsOtpTest_autoOtpInitiateNegativeTest(){
        //Invalid mobile Flow
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,INVALID_NUMBER,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String status=response.getStatus();
        String message=response.getMessage();
        String error=response.getError();
        Assert.assertEquals(message,"OTP generation error");
        Assert.assertEquals(error,"Forbidden");
        Assert.assertEquals(status,"403");
        //Invalid OtpSource
        Response errorResponse=bbpsinitiateotpForWrongSource(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,"RANDOMVALUE",BBPS_OTP_SOURCE,BILLER_NAME);
        Assert.assertEquals(errorResponse.statusCode(),400);

    }
    @SneakyThrows
    @Test(priority = 4)
    @Description("Here will validate scenarios for validate otp api if we are using different mobile number in validate api")
    public void bbpsOtpTest_autoOtpValidateWithWrongMobileNumberTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        resendOtpResponsePojo resendotp= otpservice.resendotp(requestId,TENANT_ID);
        OTP_VALUE=resendotp.getOtpValue();
        bbpsValidateOtpResponse validateOtpResponse=bbpsvalidateotp(CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE
                ,CLIENT_REF_ID,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,OTP_VALUE,requestId);
        int status=Integer.parseInt(validateOtpResponse.getStatus());
        String message=validateOtpResponse.getMessage();
        String error=validateOtpResponse.getError();
        Assert.assertTrue(message.contains("Bad credentials for user 7848862348. Remaining Attempts are:"));
        Assert.assertEquals(error,"Forbidden");
        Assert.assertEquals(status,403);
    }
    @SneakyThrows
    @Test(priority = 5)
    @Description("Here will validate scenarios for validate otp api if we are using wrong RequestId and correct Otp")
    public void bbpsOtpTest_autoOtpValidateWithWrongRequestIdAndCorrectOtpTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        resendOtpResponsePojo resendotp= otpservice.resendotp(requestId,TENANT_ID);
        OTP_VALUE=resendotp.getOtpValue();
        bbpsValidateOtpResponse validateOtpResponse=bbpsvalidateotp(CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE
                ,CLIENT_REF_ID,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,OTP_VALUE,"abcdef1111111");
        String status=validateOtpResponse.getStatus();
        String message=validateOtpResponse.getMessage();
        String error=validateOtpResponse.getError();
        Assert.assertTrue(message.contains("Bad credentials for user 7848862348. Remaining Attempts are:"));
        Assert.assertEquals(error,"Forbidden");
        Assert.assertEquals(status,"403");
    }
    @SneakyThrows
    @Test(priority = 6)
    @Description("Here will validate scenarios for validate otp api if we are using correct RequestId and Wrong Otp")
    public void bbpsOtpTest_autoOtpValidateWithCorrectRequestIdAndInCorrectOtpTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        resendOtpResponsePojo resendotp= otpservice.resendotp(requestId,TENANT_ID);
        OTP_VALUE=resendotp.getOtpValue();
        bbpsValidateOtpResponse validateOtpResponse=bbpsvalidateotp(CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE
                ,CLIENT_REF_ID,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,"123456",requestId);
        int status=Integer.parseInt(validateOtpResponse.getStatus());
        String message=validateOtpResponse.getMessage();
        String error=validateOtpResponse.getError();
        Assert.assertTrue(message.contains("Bad credentials for user 7848862348. Remaining Attempts are:"));
        Assert.assertEquals(error,"Forbidden");
        Assert.assertEquals(status,403);
    }
    @SneakyThrows
    @Test(priority = 7)
    @Description("Here will validate IVR scenarios for correct reqest params")
    public void bbpsOtpTest_userAuthenticationWhenUserPressOne(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        IVR_ID=getIvrId("ivr_id",String.valueOf(IVR_REQUEST_ID));
        Response callbackapiresponse=bbpsCallBack(IVR_ID,mobile,"SUCCESS","SUCCESS","5","1");
        String IVR_STATUS=getIvrStatus("status",String.valueOf(IVR_REQUEST_ID));
        String IVR_CALL_STATUS=getIvrCallStatus("callStatus",String.valueOf(IVR_REQUEST_ID));
        Assert.assertEquals(callbackapiresponse.statusCode(),200);
        Assert.assertEquals(IVR_STATUS,"SUCCESS");
        Assert.assertEquals(IVR_CALL_STATUS,"SUCCESS");
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }
    @SneakyThrows
    @Test(priority = 8)
    @Description("Here will validate IVR scenarios for correct reqest params")
    public void bbpsOtpTest_userAuthenticationWhenUserPressZero(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        IVR_ID=getIvrId("ivr_id",String.valueOf(IVR_REQUEST_ID));
        Response callbackapiresponse=bbpsCallBack(IVR_ID,mobile,"SUCCESS","SUCCESS","5","0");
        String IVR_STATUS=getIvrStatus("status",String.valueOf(IVR_REQUEST_ID));
        String IVR_CALL_STATUS=getIvrCallStatus("callStatus",String.valueOf(IVR_REQUEST_ID));
        Assert.assertEquals(callbackapiresponse.statusCode(),200);
        Assert.assertEquals(IVR_STATUS,"FAILED");
        Assert.assertEquals(IVR_CALL_STATUS,"User declined IVR validation");
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }
    @SneakyThrows
    @Test(priority = 9)
    @Description("Here will validate IVR scenarios for correct reqest params")
    public void bbpsOtpTest_userAuthenticationWhenUserPressAnyKeyExceptZeroOrOne(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        IVR_ID=getIvrId("ivr_id",String.valueOf(IVR_REQUEST_ID));
        Response callbackapiresponse=bbpsCallBack(IVR_ID,mobile,"FAILED","Invalid Input","5","-2");
        String IVR_STATUS=getIvrStatus("status",String.valueOf(IVR_REQUEST_ID));
        String IVR_CALL_STATUS=getIvrCallStatus("callStatus",String.valueOf(IVR_REQUEST_ID));
        Assert.assertEquals(callbackapiresponse.statusCode(),200);
        Assert.assertEquals(IVR_STATUS,"FAILED_FROM_KARIX");
        Assert.assertEquals(IVR_CALL_STATUS,"Invalid Input");
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }
    @SneakyThrows
    @Test(priority = 10)
    @Description("Here will validate IVR scenarios when user didn't pick the call.")
    public void bbpsOtpTest_userAuthenticationWhenUserDidNotPickTheCall(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        IVR_ID=getIvrId("ivr_id",String.valueOf(IVR_REQUEST_ID));
        Response callbackapiresponse=bbpsCallBack(IVR_ID,mobile,"IVR Failed from Karix","Ring Timeout","0","");
        String IVR_STATUS=getIvrStatus("status",String.valueOf(IVR_REQUEST_ID));
        String IVR_CALL_STATUS=getIvrCallStatus("callStatus",String.valueOf(IVR_REQUEST_ID));
        Assert.assertEquals(callbackapiresponse.statusCode(),200);
        Assert.assertEquals(IVR_STATUS,"FAILED_FROM_KARIX");
        Assert.assertEquals(IVR_CALL_STATUS,"Ring Timeout");
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }
    @SneakyThrows
    @Test(priority = 11)
    @Description("Here will validate IVR scenarios when call didnot get placed")
    public void bbpsOtpTest_userAuthenticationWhenCallDidNotGetPlaced(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        IVR_ID=getIvrId("ivr_id",String.valueOf(IVR_REQUEST_ID));
        Response callbackapiresponse=bbpsCallBack(IVR_ID,mobile,"IVR Failed from Karix","Network error","0","-1");
        String IVR_STATUS=getIvrStatus("status",String.valueOf(IVR_REQUEST_ID));
        String IVR_CALL_STATUS=getIvrCallStatus("callStatus",String.valueOf(IVR_REQUEST_ID));
        Assert.assertEquals(callbackapiresponse.statusCode(),200);
        Assert.assertEquals(IVR_STATUS,"FAILED_FROM_KARIX");
        Assert.assertEquals(IVR_CALL_STATUS,"Network error");
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }
    @SneakyThrows
    @Test(priority = 12)
    @Description("Here will validate IVR scenarios for wrong ivr_id params")
    public void bbpsOtpTest_userAuthenticationWhenpassingWrongIvrId(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        bbpsCallBackResponse callbackapiresponse=bbpsCallBackError("12345678910",mobile,"IVR Failed from Karix","Network error","0","-1");
        int statusCode=callbackapiresponse.getStatus();
        String message=callbackapiresponse.getMessage();
        String error=callbackapiresponse.getError();
        Assert.assertEquals(statusCode,400);
        Assert.assertEquals(message,"Ivr ID is invalid");
        Assert.assertEquals(error,"Bad Request");
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }
    @SneakyThrows
    @Test(priority = 13)
    @Description("The test will verify if the otp status is validated then if user want to initiate auto_otp flow,user will get new otp")
    public void bbpsOtpTest_autoOtpValidationTestInCaseOtpIsValidated(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        resendOtpResponsePojo resendotp= otpservice.resendotp(requestId,TENANT_ID);
        OTP_VALUE=resendotp.getOtpValue();
        bbpsValidateOtpResponse validateOtpResponse=bbpsvalidateotp(CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO
                ,CLIENT_REF_ID,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,OTP_VALUE,requestId);
        String requestIdValidateOtp=validateOtpResponse.getRequestId();
        String authType=validateOtpResponse.getAuthType();
        String status=validateOtpResponse.getStatus();
        Assert.assertEquals(requestId,requestIdValidateOtp,"RequestId from initiateOtp and ValidateOtp doesn't match");
        Assert.assertEquals(authType,AUTO_OTP_SOURCE);
        Assert.assertEquals(status,"SUCCESS");
        CLIENT_REF_ID=clientRefIdGenerator();
        response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestIdNew=response.getRequestId();
        resendotp= otpservice.resendotp(requestId,TENANT_ID);
        String OTP_VALUE_NEW=resendotp.getOtpValue();
        Assert.assertNotEquals(requestId,requestIdNew);
        Assert.assertNotEquals(OTP_VALUE,OTP_VALUE_NEW);
        deleteRequestIdDataFromAuthenticationOtp(String.valueOf(requestId));
        deleteRequestIdDataFromAuthenticationOtp(String.valueOf(requestIdNew));

    }
    @SneakyThrows
    @Test(priority = 14)
    @Description("The test will verify if the otp is expired then the validation with expired OTP should throw error")
    public void bbpsOtpTest_autoOtpValidationWillFailInCaseOtpIsExpired(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,3,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        resendOtpResponsePojo resendotp= otpservice.resendotp(requestId,TENANT_ID);
        OTP_VALUE=resendotp.getOtpValue();
        String expiryIn=response.getExpiresIn();
        Assert.assertEquals(expiryIn,"0m:03s");
        Thread.sleep(3000);
        CLIENT_REF_ID=clientRefIdGenerator();
        bbpsValidateOtpResponse validateOtpResponse=bbpsvalidateotp(CLIENT_ID_ONE,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_ONE
                ,CLIENT_REF_ID,AUTO_OTP_SOURCE,BBPS_OTP_SOURCE,OTP_VALUE,requestId);
        int status=Integer.parseInt(validateOtpResponse.getStatus());
        String message=validateOtpResponse.getMessage();
        String error=validateOtpResponse.getError();
        Assert.assertTrue(message.contains("Bad credentials for user 7848862348. Remaining Attempts are:"));
        Assert.assertEquals(error,"Forbidden");
        Assert.assertEquals(status,403);
        deleteRequestIdDataFromAuthenticationOtp(requestId);
    }
    @SneakyThrows
    @Test(priority = 15)
    @Description("Here will validate if one ivr request is initiated then for same number another call not possible")
    public void bbpsOtpTest_multipleIvrCallsToSameNumberAtSameTimeTest(){
        String CLIENT_REF_ID=clientRefIdGenerator();
        bbpsInitiateOtpResponse response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestId=response.getRequestId();
        String mobile=response.getMobile();
        IVR_REQUEST_ID=getIvrRequestId("id",mobile);
        Assert.assertEquals(Integer.parseInt(requestId),IVR_REQUEST_ID);
        Thread.sleep(2000);
        updateIvrStatus(requestId);
        updateIvrCallStatusAndDetails(requestId);
        response=bbpsinitiateotp(CLIENT_ID_ONE
                ,EXISTING_USER_MOBILE_NUMBER_LOGIN_FLOW_TWO,CLIENT_REF_ID,360,IVR_SOURCE,BBPS_OTP_SOURCE,BILLER_NAME);
        String requestIdNew=response.getRequestId();
        Assert.assertEquals(requestId,requestIdNew);
        deleteRequestIdDataFromAuthenticationIvr(requestId);
    }


}
