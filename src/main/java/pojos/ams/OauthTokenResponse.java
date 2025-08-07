package pojos.ams;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthTokenResponse {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public Integer expires_in;
    public String scope;
    public PrepaidPayToken Prepaid_Pay_Token;
    public String client_type;
    public String chat_bot_token;
    public String chat_bot_token_expires_in;


    //Error response
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;

}
