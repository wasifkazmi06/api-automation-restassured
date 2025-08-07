package pojos.Xpress;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScrubAssessmentResponsePojo {
    public String id;
    public Object createdAt;
    public String assessmentRequestId;
    public Object request;
    public Response response;
    public boolean isSuccess;
    public String breType;
    public Integer policyVersion;
    public Integer rbpPolicyVersion;
    public Object requestAt;
    public Object responseAt;
    public String assessmentKind;
    public String externalServiceStatus;
    public String message;
    public String status;
    public String serviceIdentifier;
}
