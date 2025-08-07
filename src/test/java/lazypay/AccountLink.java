package lazypay;

import api.lazypay.transaction.SendOTP;
import api.lazypay.transaction.ValidateOTP;
import io.restassured.response.Response;
import lazypay.transactionFlow.GenerateSignature;
import lazypay.transactionFlow.TransactionData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.lazypay.transactionFlow.SendOTPPojo;
import pojos.lazypay.transactionFlow.ValidateOTPPojo;
import util.FindOrCreateUserOTP;
import util.StringTemplate;
import java.util.HashMap;
import java.util.Map;


public class AccountLink {

    public static String signatureSendOTP;
    public static String signatureValidateOTP;
    public static SendOTP sendOTP;
    public static ValidateOTP validateOTP;
    public static FindOrCreateUserOTP findOrCreateUserOTP;
    public static ValidateOTPPojo validateOTPPojo;

    public static Response sendOTPResponse;
    public static String otp = null;

    static {
        try {
            sendOTP = new SendOTP();
            findOrCreateUserOTP = new FindOrCreateUserOTP();
            validateOTP = new ValidateOTP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void accountLink() throws Exception {

        accountLinkMethod(MakeTransaction.mobile, MakeTransaction.email, MakeTransaction.merchantAccessKey);
        Assert.assertEquals(sendOTPResponse.statusCode(), 200, "Check that the user is correctly whitelisted from DS");
        Assert.assertNotNull(validateOTPPojo.access_token, "Check that the user is correctly whitelisted from DS");
    }

    public static void accountLinkMethod(String mobile, String email, String merchantAccessKey) throws Exception {

        Map<String, Object> queryParamDetails = new HashMap<>();
        HashMap<String, String> headerDetails = new HashMap<>();

        signatureSendOTP = GenerateSignature.generateSignature("merchantAccessKey=" + merchantAccessKey +
                "&mobile=" + mobile + "&email=" + email, merchantAccessKey);

        headerDetails.put("signature", signatureSendOTP);
        headerDetails.put("accessKey", merchantAccessKey);

        String sendOTPRequest= new StringTemplate(LazypayConstants.SEND_OTP)
                .replace("mobile", mobile)
                .replace("email", email)
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("firstName", null)
                .replace("lastName", null)
                .toString();

        sendOTPResponse = sendOTP.postReturnResponse(queryParamDetails, headerDetails, sendOTPRequest);

        otp = FindOrCreateUserOTP.getLatestOTP(mobile);
        signatureValidateOTP = GenerateSignature.generateSignature("merchantAccessKey=" + merchantAccessKey +
                "&mobile=" + mobile + "&email=" + email + "&otp=" + otp, merchantAccessKey);

        headerDetails.put("signature", signatureValidateOTP);
        headerDetails.put("accessKey", merchantAccessKey);

        String validateOTPRequest= new StringTemplate(LazypayConstants.VALIDATE_OTP)
                .replace("mobile", mobile)
                .replace("email", email)
                .replace("source", TransactionData.SOURCE_CITRUS_SDK)
                .replace("otp", otp)
                .replace("firstName", null)
                .replace("lastName", null)
                .toString();

        validateOTPPojo = validateOTP.post(queryParamDetails, headerDetails, validateOTPRequest);
    }
}