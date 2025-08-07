package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenPojo {

    public String access_token;
    public String token_type;
    public String refresh_token;
    public String expires_in;
    public String scope;

}


