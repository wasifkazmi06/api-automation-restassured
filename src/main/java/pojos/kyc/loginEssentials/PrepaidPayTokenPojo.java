package pojos.kyc.loginEssentials;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PrepaidPayTokenPojo {
        public String access_token;
        public String token_type;
        public int expires_in;
        public String scope;
        public String Outer_Access_Token;
        public String client_type;

}
