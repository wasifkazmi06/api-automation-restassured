package pojos.paylaterOnboarding;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class PLValidateOTPPojo {
    public String mobile;
    public String accessToken;
    public Date accessTokenExpiry;
    public String clientId;
    public String clientSecret;
    public String errorCode;
    public String message;
}
