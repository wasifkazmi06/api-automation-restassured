package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class bbpsValidateOtpResponse {
    public String requestId;
    public String mobile;
    public String authType;
    public String status;
    public Object reason;

    //For error response
    public String timestamp;
    public String error;
    public String message;
    public String path;
}
