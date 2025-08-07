package pojos.otpservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class getPolicyResponsePojo {
    public Integer policyId;
    public String tenantId;
    public String policyName;
    public OtpConfig otpConfig;
    public Integer maxValidationAttempts;
    public Integer maxResendAttempts;
    //Error Response
    public String internalCode;
    public String internalMessage;
    public String endUserMessage;

}
