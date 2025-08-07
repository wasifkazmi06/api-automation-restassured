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
public class KycStatusResponsePojo {
    public String userId;
    public Long kycCaseId;
    public String kycStatus;
    public String kycState;
    public String kycTypeId;
    public int kycVersion;
    public String message;
    public String kycStatusMessage;
    public String kycCategory;
    public List<String> products;
    public String lastUpdated;
    public String dateCreated;
}
