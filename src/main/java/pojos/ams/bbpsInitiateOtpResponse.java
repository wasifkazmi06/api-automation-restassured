package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class bbpsInitiateOtpResponse {

    public String requestId;
    public String mobile;
    public String expiresIn;
    public String billerName;
    public String status;

    //For error Response
    public String timestamp;
    public String error;
    public String message;
    public String path;
}
