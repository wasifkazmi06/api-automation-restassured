package pojos.kyc.apis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateKYCV9Pojo {
    public String kycCaseId;
    public String kycStatus;
    public String message;
    public List<DocumentsPendingPojo> documentsPending;
    public List<Object> documentsReceived;
    public ConsentPendingPojo consentPending;
    public String kycTypeId;
    public Object reason;
    public ResponseMetadataPojo responseMetadata;
    public String errorCode;
    public String error;
    public String status;

}
