package pojos.lazypay.juspay.repaymentFlow;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpOAuthTokenPojo {
        String authToken;
        String refreshToken;
}

