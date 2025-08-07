package pojos.kyc.loginEssentials;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OtpPojo {
    public String responseCode;
    public String responseMessage;
    public ResponseDataPojo responseData;
}
