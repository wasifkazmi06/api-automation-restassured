package pojos.kyc.loginEssentials;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OauthTokenResponse {
        public String access_token;
        public String token_type;
        public String refresh_token;
        public int expires_in;
        public String scope;
        public PrepaidPayTokenPojo Prepaid_Pay_Token;
        public String client_type;
}
