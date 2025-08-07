package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class bbpsValidateOtpRequestPojo {
    public String requestId;
    public String mobile;
    public String clientId;
    public String clientRefId;
    public String otpValue;
    public String otpSource;
    public String source;
}
