package pojos.lazypay.juspay.repaymentFlow;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerifyOtpPojo {

    String userId;
    String umUuid;
    String deviceBindingId;
    public VerifyOtpOAuthTokenPojo oauthTokenResponse;
    Object fullName;
    Object primaryEmail;
    Object primaryMobile;
    Object dob;

    //error codes
    public String message;
    public String path;
    public String status;
    public String title;
    public String errorCode;
}

