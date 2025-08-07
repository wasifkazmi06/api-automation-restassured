package pojos.lazypay.juspay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseData {

    public Object limitedAccessToken;
    public Object profileData;
    public String otpValue;

}