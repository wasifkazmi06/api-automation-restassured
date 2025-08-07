package pojos.upi.convertToEMI;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class EMITransactionEligibilityPojo {


    public String uuid;
    public String creditLimit;
    public String outstanding;
    public String responseCode;
    public String errorCode;
    public List<TenureInfoPojo> tenureInfoMap;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
}
