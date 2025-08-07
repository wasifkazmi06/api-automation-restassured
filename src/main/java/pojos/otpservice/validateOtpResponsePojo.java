package pojos.otpservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class validateOtpResponsePojo {
    public Boolean valid;
    //Error Response
    public String internalCode;
    public String internalMessage;
    public String endUserMessage;
}
