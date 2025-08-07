package pojos.kyc.apis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KYCStatusPojo {
    public String userId;
    public String kycCaseId;
    public String kycStatus;
    public String kycState;
    public String kycTypeId;
    public String kycVersion;
    public String message;
    public String kycStatusMessage;
    public String kycCategory;
    public List<Object> products;
    public String lastUpdated;
    public String dateCreated;
    public String timestamp;
    public String status;
    public String error;
    public String path;
    public String errorCode;
    public String metadata;
    public String uuid;
    public String nameOnPan;
    public String panNumber;

}


