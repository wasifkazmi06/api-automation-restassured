package platform.otp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.otpservice.FieldError;
import pojos.otpservice.generateOtpResponsePojo;
import pojos.otpservice.resendOtpResponsePojo;
import pojos.otpservice.validateOtpResponsePojo;

import java.util.List;

public class otpApiTest extends otpserviceutilities {

    public otpApiTest() throws Exception {
    }

    String OTP_ID;
    String ResendDelayInSeconds;
    String TENANT_ID="ec35b493";

    @SneakyThrows
    @Test(priority = 1, dataProvider = "otpGenerationData")
    @Description("The test will validate generate otp response for different policies")
    public void generateOtpTest(String otpGenerationData, int policyId, String tenantId, int length) {
        generateOtpResponsePojo otpResponse = generateotp(policyId, tenantId);
        OTP_ID = otpResponse.getOtpId();
        String otpValue = otpResponse.getOtpValue();
        ResendDelayInSeconds= otpResponse.getResendDelayInSeconds();
        Assert.assertEquals(otpValue.length(), length, "The OTP length is not as per the expected length " + length + " digit");
        resendOtpResponsePojo resendOtpResponse=resendotp(OTP_ID,tenantId);
        Assert.assertEquals(resendOtpResponse.getOtpValue(),otpValue,"OTP not matching in case of generated but not expired otp");
        validateOtpResponsePojo validateOtpResponse=validateotp(OTP_ID,otpValue,tenantId,ResendDelayInSeconds);
        boolean isValid=validateOtpResponse.getValid();
        Assert.assertEquals(isValid,true);
        resendOtpResponsePojo resendOtpResponseAfterValidation=resendotp(OTP_ID,tenantId);
        Assert.assertEquals(resendOtpResponseAfterValidation.getInternalCode(),"OTP-1002");
        Assert.assertEquals(resendOtpResponseAfterValidation.getInternalMessage(),"Otp already validated");
        Assert.assertEquals(resendOtpResponseAfterValidation.getEndUserMessage(),"OTP already validated, Please generate new OTP");
        if (otpGenerationData.equals("SECUREAPP-WEBAPP_TRANSACTIONS-ALPHANUMERIC_V1")) {
            String excludeCharacters = "iIoOl";
            Assert.assertTrue(StringUtils.isAlphanumeric(otpValue), "The OTP Type is numeric");
            Assert.assertTrue(isOtpContainsExcludeCharacter(otpValue, excludeCharacters));
            Assert.assertFalse(isOtpContatinSpecialCharater(otpValue));
        } else {
            Assert.assertTrue(StringUtils.isNumeric(otpValue), "The OTP Type is alphanumeric");
        }
    }
    @SneakyThrows
    @Test(priority = 2, dataProvider = "otpGenerationDataNegativeCase")
    @Description("The test will validate generate otp response for different policies")
    public void generateOtpTestNegative(String otpGenerationDataNegativeCase, int policyId, String tenantId,
                                        String internalCode,String internalmessage,String endUserMessage) {
        generateOtpResponsePojo otpResponse = generateotp(policyId, tenantId);
        Assert.assertEquals(otpResponse.getInternalCode(),internalCode,"Error code not matching");
        Assert.assertEquals(otpResponse.getInternalMessage(),internalmessage,"Internal message not matching");
        Assert.assertEquals(otpResponse.getEndUserMessage(),endUserMessage,"End User message not matching");
        if (!otpGenerationDataNegativeCase.equals("WRONG_POLICY_ID")) {
            ObjectMapper mapper=new ObjectMapper();
            String errorString=mapper.writeValueAsString(otpResponse.getFieldErrors());
            List<FieldError> jsonObject=mapper.readValue(errorString,mapper.getTypeFactory().constructCollectionType(List.class, FieldError.class));
            for(FieldError fielderror:jsonObject){
                String field=fielderror.getField();
                Assert.assertEquals(field,"tenantId");
                String message=fielderror.getMessage();
                Assert.assertEquals(message,"Tenant ID must be 8 characters long");
                String code=fielderror.getCode();
                Assert.assertEquals(code,"Size");
            }
        }
    }
    @SneakyThrows
    @Test(priority = 3)
    @Description("The test will validate resendOtp Will generate new Otp ID and otp value if otp is Generated and expired")
    public void resendOtpWithExpiredOtpId(){
        generateOtpResponsePojo otpResponse = generateotp(48,TENANT_ID);
        OTP_ID=otpResponse.getOtpId();
        String OTP_VALUE=otpResponse.getOtpValue();
        Thread.sleep(6000);
        resendOtpResponsePojo resendotpresponse=resendotp(OTP_ID,TENANT_ID);
        String NEW_OTP_ID=resendotpresponse.getOtpId();
        String NEW_OTP_VALUE=resendotpresponse.getOtpValue();
        Assert.assertNotSame(NEW_OTP_ID,OTP_ID);
        Assert.assertNotSame(NEW_OTP_VALUE,OTP_VALUE);
    }
    @SneakyThrows
    @Test(priority = 4)
    @Description("The test will validate for resend otp api if we are passing different tenantId it will throw error.")
    public void resendOtpWithDifferentTenantId(){
        generateOtpResponsePojo otpResponse = generateotp(1,TENANT_ID);
        OTP_ID=otpResponse.getOtpId();
        resendOtpResponsePojo resendotpresponse=resendotp(OTP_ID,"PAYUFINN");
        Assert.assertEquals(resendotpresponse.getInternalCode(),"OTP-1002");
        Assert.assertEquals(resendotpresponse.getInternalMessage(),"Invalid tenant ID, Please send the correct tenant ID.");
        Assert.assertEquals(resendotpresponse.getEndUserMessage(),"Unauthorized");
    }
    @SneakyThrows
    @Test(priority = 5)
    @Description("The test will validate for resend otp api if we are passing different tenantId it will throw error.")
    public void validateOtpWithWrongOtpAndOtpId(){
        generateOtpResponsePojo otpResponse = generateotp(1,TENANT_ID);
        String OTP_ID=otpResponse.getOtpId();
        String OTP_VALUE=otpResponse.getOtpValue();
        validateOtpResponsePojo validateOtpResponseOne=validateotp(OTP_ID,OTP_VALUE+1,TENANT_ID,ResendDelayInSeconds);
        boolean isValid=validateOtpResponseOne.getValid();
        Assert.assertEquals(isValid,false);
        validateOtpResponseOne=validateotp(OTP_ID+1,OTP_VALUE,TENANT_ID,ResendDelayInSeconds);
        Assert.assertEquals(validateOtpResponseOne.getInternalCode(),"OTP-1002");
        Assert.assertEquals(validateOtpResponseOne.getInternalMessage(),"Invalid Otp id passed!");
        Assert.assertEquals(validateOtpResponseOne.getEndUserMessage(),"Invalid OTP, Please generate new OTP");
    }
    @SneakyThrows
    @Test(priority = 6)
    @Description("The test will validate resend attempt and validation attempt")
    public void maxResensAttemptAndMaxValidationAttemptTest(){
        resendOtpResponsePojo resendotpresponse=new resendOtpResponsePojo();
        validateOtpResponsePojo validateotpresponse=new validateOtpResponsePojo();
        generateOtpResponsePojo otpResponse = generateotp(1,TENANT_ID);
        String OTP_ID=otpResponse.getOtpId();
        for(int i=0;i<11;i++){
            resendotpresponse=resendotp(OTP_ID,TENANT_ID);
        }
      // int maxResendAttempt=getNumberOfResendAttemptFromOtpTable("resend_attempts",OTP_ID);
      // Assert.assertEquals(maxResendAttempt,5);
        Assert.assertEquals(resendotpresponse.getInternalCode(),"OTP-1002");
        Assert.assertEquals(resendotpresponse.getInternalMessage(),"Otp resend attempt exceeded");
        Assert.assertEquals(resendotpresponse.getEndUserMessage(),"Otp resend attempt exceeded, Please generate new OTP");
        for(int i=0;i<6;i++){
            validateotpresponse=validateotp(OTP_ID,"123456",TENANT_ID,ResendDelayInSeconds);
        }
       // int maxValidationAttempt=getNumberOfValidationAttemptFromOtpTable("validation_attempts",OTP_ID);
       // Assert.assertEquals(maxValidationAttempt,5);
        Assert.assertEquals(validateotpresponse.getInternalCode(),"OTP-1002");
        Assert.assertEquals(validateotpresponse.getInternalMessage(),"Otp Validation attempt exceeded");
        Assert.assertEquals(validateotpresponse.getEndUserMessage(),"Otp Validation attempt exceeded, Please generate new OTP");

    }


}
