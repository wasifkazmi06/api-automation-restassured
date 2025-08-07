package pojos.paylaterOnboarding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PLInitiateOTPPojo {
    public boolean status;
    public String otpType;
    public String errorCode;
    public String message;
}
