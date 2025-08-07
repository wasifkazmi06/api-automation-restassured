package pojos.otpservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class createPolicyRequestPojo {
    public String policyName;
    public String tenantId;
    public OtpConfig otpConfig;
    public Integer maxValidationAttempts;
    public Integer maxResendAttempts;
}
