package pojos.lazypay.transactionFlow;

import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetOTPByIDPojo {

    public String otpId;
    public String otpValue;
    public String resendDelayInSeconds;

    /**
     * For error
     */
    public String timestamp;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public String internalCode;
    public String internalMessage;
    public String endUserMessage;
}
