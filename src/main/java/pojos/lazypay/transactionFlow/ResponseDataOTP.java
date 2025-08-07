package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseDataOTP {
    public Object limitedAccessToken;
    public Object profileData;
    public String otpValue;
    public String otpId;

}


