package pojos.upi.bnpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LPTransactionEligibilityPojo {

    public String uuid;
    public String creditLimit;
    public String outstanding;
    public String responseCode;
    public String errorCode;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String exception;


}
