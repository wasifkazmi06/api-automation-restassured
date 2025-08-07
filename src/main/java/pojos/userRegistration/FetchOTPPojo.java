package pojos.userRegistration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FetchOTPPojo {

    public String otpId;
    public String otpValue;

    //For Error
    public String internalCode;
    public String internalMessage;
    public String endUserMessage;
}