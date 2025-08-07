package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class bbpsInitiateOtpRequestPojo {
    public String mobile;
    public String clientId;
    public String clientRefId;
    public String otpType;
    public String otpFormat;
    public Integer otpExpiry;
    public String otpSource;
    public String source;
    public SourceRequest sourceRequest;
}
