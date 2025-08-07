package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
public class FindUserOTPPojo {

    public String responseCode;
    public String responseMessage;
    public ResponseDataOTP responseData;
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


