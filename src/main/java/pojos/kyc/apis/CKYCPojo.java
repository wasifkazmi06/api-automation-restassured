package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CKYCPojo {
    public String ckycNumber;
    public String kycStatus;
    public String kycDateUpdated;

    // error response
    public long timestamp;
    public int status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public Object metadata;
}
