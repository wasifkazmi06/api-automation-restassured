package mbe.authentication;

import api.lazypay.juspay.repayment.LoginRegister;
import api.otpservice.GetOtp;
import io.qameta.allure.Step;
import pojos.lazypay.juspay.repaymentFlow.SendOtpPojo;
import pojos.otpservice.GetOtpResponsePojo;
import java.util.HashMap;

public class APPLoginUser {

    public static SendOtpPojo sendOtpPojo=new SendOtpPojo();
    public static GetOtpResponsePojo getOtpResponsePojo=new GetOtpResponsePojo();
    public static GetOtp getOtp;

    public static String OauthToken;

    public APPLoginUser() throws Exception {
    }
    static LoginRegister loginRegister;

    static {
        try {
            loginRegister = new LoginRegister();
            getOtp=new GetOtp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Step
    public static String LoginIntoApp(String userMobile) throws Exception {
        String otpId = SendOtp.SendingOTP(userMobile);

        String otpValue=GetOTPFromOTPService(userMobile,otpId);
         OauthToken = VerifyOtp.verifyOTP(userMobile, otpValue, otpId);

        return OauthToken;
    }

    @Step
    public static String GetOTPFromOTPService(String userMobile, String otpId) throws Exception {

        HashMap<String, Object> queryParamDetails = new HashMap<String,Object>();
        HashMap<String, String> headerDetails = new HashMap<>();

        headerDetails.put("accept", "application/json");

        queryParamDetails.put("tenantId",AuthTestData.tenantId);
        queryParamDetails.put("otpId",otpId);

        getOtpResponsePojo = getOtp.get(queryParamDetails, headerDetails,"");

        String otpValue=getOtpResponsePojo.getOtpValue();
        return otpValue;
    }

}
