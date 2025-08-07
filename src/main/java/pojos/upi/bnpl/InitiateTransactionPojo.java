package pojos.upi.bnpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiateTransactionPojo {

    public String txnRefNo;
    public String dueDate;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
}
