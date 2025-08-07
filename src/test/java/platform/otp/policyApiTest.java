package platform.otp;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;

import lombok.SneakyThrows;

import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.otpservice.FieldError;
import pojos.otpservice.createPolicyResponsePojo;
import pojos.otpservice.deletePolicyResponsePojo;
import pojos.otpservice.getPolicyResponsePojo;


import java.util.List;

public class policyApiTest extends otpserviceutilities {

    public policyApiTest() throws Exception{
    }

    int POLICY_ID;
    @SneakyThrows
    @Test(priority = 1,dataProvider = "policyCreationData")
    @Description("The test will create policy and validate the successful response")
    public void policyCreationTest(String policyCreationData, String otpType, int otpLength, int mindigit,int minalphabet,
                                   String excludedigit, String excludealphabet, int expiryTime,String policyName,
                                   int maxValidationAttempt,int maxResendAttempt,String successMessage,
                                   String internalCode,String InternalMessage,String endUserMessage){
        createPolicyResponsePojo createPolicyResponse=createPolicy(otpType,otpLength,mindigit,minalphabet,excludedigit,excludealphabet,
                expiryTime,policyName,maxValidationAttempt,maxResendAttempt);
        if(policyCreationData.equals("CREATE_POLICY_NUMERIC_OTP")||policyCreationData.equals("CREATE_POLICY_ALPHANUMERIC_OTP")) {
            POLICY_ID = createPolicyResponse.getPolicyId();
            Assert.assertEquals(createPolicyResponse.getMessage(), successMessage);
            getPolicyResponsePojo getPolicyResponse = getPolicy(POLICY_ID);
            Assert.assertEquals(getPolicyResponse.getPolicyName(), policyName);
            Assert.assertEquals(getPolicyResponse.getTenantId(), "TESTTEST");
            Assert.assertEquals(Integer.parseInt(getPolicyResponse.getMaxResendAttempts().toString()), maxResendAttempt);
            Assert.assertEquals(Integer.parseInt(getPolicyResponse.getMaxValidationAttempts().toString()), maxValidationAttempt);
            Assert.assertEquals(getPolicyResponse.getOtpConfig().getOtpType(), otpType);
            Assert.assertEquals(Integer.parseInt(getPolicyResponse.getOtpConfig().getOtpLength().toString()), otpLength);
            Assert.assertEquals(getPolicyResponse.getOtpConfig().getExcludeDigits(), excludedigit);
            Assert.assertEquals(Integer.parseInt(getPolicyResponse.getOtpConfig().getOtpExpiryInSeconds().toString()), expiryTime);
            deletePolicyResponsePojo response = deletePolicy(POLICY_ID);
            Assert.assertEquals(Integer.parseInt(response.getPolicyId().toString()), POLICY_ID);
            Assert.assertEquals(response.getMessage(), "Successfully deleted policy.");
        } else if(policyCreationData.equals("CREATE_POLICY_NUMERIC_OTP_WITHOUT_MIN_DIGIT")){
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);
            ObjectMapper mapper=new ObjectMapper();
            String errorString=mapper.writeValueAsString(createPolicyResponse.getFieldErrors());
            List<FieldError> jsonObject=mapper.readValue(errorString,mapper.getTypeFactory().constructCollectionType(List.class, FieldError.class));
            for(FieldError fielderror:jsonObject){
                String field=fielderror.getField();
                Assert.assertEquals(field,"otpConfig.minimumNumberOfDigits");
                String message=fielderror.getMessage();
                Assert.assertEquals(message,"must not be null");
                String code=fielderror.getCode();
                Assert.assertEquals(code,"NotNull");
            }

        }else if(policyCreationData.equals("CREATE_POLICY_ALPHANUMERIC_OTP_WITHOUT_MIN_ALPHABET")){
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);
            ObjectMapper mapper=new ObjectMapper();
            String errorString=mapper.writeValueAsString(createPolicyResponse.getFieldErrors());
            List<FieldError> jsonObject=mapper.readValue(errorString,mapper.getTypeFactory().constructCollectionType(List.class, FieldError.class));
            for(FieldError fielderror:jsonObject){
                String field=fielderror.getField();
                Assert.assertEquals(field,"otpConfig.minimumNumberOfAlphabets");
                String message=fielderror.getMessage();
                Assert.assertEquals(message,"must not be null");
                String code=fielderror.getCode();
                Assert.assertEquals(code,"NotNull");
            }
        }else if(policyCreationData.equals("CREATE_NUMERIC_OTP_WHERE_MIN_DIGIT_GREATER_THAN_OTP_LENGTH")){
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);

        }else if(policyCreationData.equals("CREATE_ALPHA_NUMERIC_OTP_WHERE_MIN_DIGIT+ALPHABET_GREATER_THAN_OTP_LENGTH")){
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);

        }else if(policyCreationData.equals("CREATE_POLICY_WHERE_MAX_VALIDATION_ATTEMPT_ZERO")){
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);
            ObjectMapper mapper=new ObjectMapper();
            String errorString=mapper.writeValueAsString(createPolicyResponse.getFieldErrors());
            List<FieldError> jsonObject=mapper.readValue(errorString,mapper.getTypeFactory().constructCollectionType(List.class, FieldError.class));
            for(FieldError fielderror:jsonObject){
                String field=fielderror.getField();
                Assert.assertEquals(field,"maxValidationAttempts");
                String message=fielderror.getMessage();
                Assert.assertEquals(message,"must be greater than or equal to 1");
                String code=fielderror.getCode();
                Assert.assertEquals(code,"Min");
            }
    }else if(policyCreationData.equals("CREATE_POLICY_WHERE_MAX_RESEND_ATTEMPT_ZERO")) {
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);
            ObjectMapper mapper = new ObjectMapper();
            String errorString = mapper.writeValueAsString(createPolicyResponse.getFieldErrors());
            List<FieldError> jsonObject = mapper.readValue(errorString, mapper.getTypeFactory().constructCollectionType(List.class, FieldError.class));
            for (FieldError fielderror : jsonObject) {
                String field = fielderror.getField();
                Assert.assertEquals(field, "maxResendAttempts");
                String message = fielderror.getMessage();
                Assert.assertEquals(message, "must be greater than or equal to 1");
                String code = fielderror.getCode();
                Assert.assertEquals(code, "Min");
            }
        }else{
            Assert.assertEquals(createPolicyResponse.getInternalCode(), internalCode);
            Assert.assertEquals(createPolicyResponse.getEndUserMessage(), InternalMessage);
            Assert.assertEquals(createPolicyResponse.getInternalMessage(), endUserMessage);
        }
    }
}
