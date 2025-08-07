package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthTokenPojo {

    public String access_token;
    public String token_type;
    public Object expires_in;
    public String scope;


    //For Error
    public String timestamp;
    public String status;
    public String error;
    public String errorCode;
    public String error_description;

}


