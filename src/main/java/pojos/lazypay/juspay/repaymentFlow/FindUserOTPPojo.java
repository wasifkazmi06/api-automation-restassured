package pojos.lazypay.juspay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserOTPPojo {

    public String responseCode;
    public String responseMessage;
    public ResponseData responseData;
    public String limitedAccessToken;
    public Object profileData;
    public String otpValue;


    //For Error
    public String timestamp;
    public String status;
    public String error;
    public String path;
    public String errorCode;
    public String requestId;
    public String availableCreditLimit;

}
