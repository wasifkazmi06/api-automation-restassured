package pojos.lazypay.transactionFlow;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateOTPPojo {

    public String access_token;
    public String token_type;
    public String refresh_token;
    public String expires_in;
    public String scope;
    public String chat_bot_token;
    public String chat_bot_token_expires_in;

    /**
     * For error
     */
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public String attemptsRemaining;
}