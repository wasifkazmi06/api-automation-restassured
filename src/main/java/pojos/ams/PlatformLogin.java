package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlatformLogin {
    public String clientId;
    public String clientSecret;
    public Object email;
    public String grantType;
    public String mobile;
    public String otp;

}
