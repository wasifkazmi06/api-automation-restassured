package pojos.otpservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class createPolicyResponsePojo {
    public Integer policyId;
    public String message;
    //Error Response
    public String internalCode;
    public String internalMessage;
    public String endUserMessage;
    public List<FieldError> fieldErrors;
}
