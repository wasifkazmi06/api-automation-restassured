package pojos.ams;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LimitedAccessToken {
    public String access_token;
    public String token_type;
    public String refresh_token;
    public Integer expires_in;
    public String scope;
}
