package lazypay.transactionFlow;

import api.lazypay.transaction.SendOTP;
import api.lazypay.transaction.ValidateOTP;
import io.restassured.response.Response;
import lazypay.LazypayConstants;
import org.testng.Assert;
import pojos.lazypay.transactionFlow.ValidateOTPPojo;
import util.FindOrCreateUserOTP;
import util.StringTemplate;

import java.util.HashMap;

public class GenerateToken {

    public static SendOTP sendOTP;

    static {
        try {
            sendOTP = new SendOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String signature = null;
    public static String otp = null;
    public static FindOrCreateUserOTP findOrCreateUserOTP;
    static int retryCount = 0;

    static {
        try {
            findOrCreateUserOTP = new FindOrCreateUserOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ValidateOTPPojo validateOTPPojo = new ValidateOTPPojo();
    public static ValidateOTP validateOTP;

    static {
        try {
            validateOTP = new ValidateOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GenerateToken() throws Exception{

    }

    public static String generateToken(String access_key, String mobile, String email, String firstName, String lastName) throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signature = GenerateSignature.generateSignature("merchantAccessKey=" + access_key + "&mobile=" + mobile +
                "&email=" + email, access_key);

        headerDetails.put("signature", signature);
        headerDetails.put("accessKey", access_key);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", mobile)
                .replace("email", email)
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", firstName)
                .replace("lastName", lastName)
                .toString();

        Response sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);
        Assert.assertEquals(sendOTPResponse.statusCode(), 200, "Check that the user is correctly whitelisted from DS");

        try{
            do{
                if(System.getProperty("env").equals("QA")){
                    otp = "1234";
                }
                else {
                    otp = FindOrCreateUserOTP.getLatestOTP(mobile);
                }
                signature = GenerateSignature.generateSignature("merchantAccessKey=" + access_key + "&mobile=" + mobile +
                        "&email=" + email + "&otp=" + otp, access_key);

                headerDetails.put("signature", signature);
                headerDetails.put("accessKey", access_key);

                String validateOTPRequest = new StringTemplate(LazypayConstants.VALIDATE_OTP)
                        .replace("mobile", mobile)
                        .replace("email", email)
                        .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                        .replace("otp", otp)
                        .replace("firstName", firstName)
                        .replace("lastName", lastName)
                        .toString();

                validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
                retryCount++;
            }while(validateOTPPojo.status.equals("401") && validateOTPPojo.errorCode.equals("LP_INCORRECT_OTP") && retryCount<=2);
        }catch(NullPointerException e){
        }

        Assert.assertNotNull(validateOTPPojo.access_token, "Check that the user is correctly whitelisted from DS");
        return validateOTPPojo.access_token;
    }


}