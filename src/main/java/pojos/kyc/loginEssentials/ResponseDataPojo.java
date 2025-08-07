package pojos.kyc.loginEssentials;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ResponseDataPojo {
    public LimitedAccessTokenPojo limitedAccessToken;
    public ProfileDataPojo profileData;
    public String otpValue;
}
