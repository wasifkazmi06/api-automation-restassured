package pojos.ams;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrepaidPayToken {
    public String access_token;
    public String token_type;
    public Integer expires_in;
    public String scope;
    public String Outer_Access_Token;
    public String client_type;
}
