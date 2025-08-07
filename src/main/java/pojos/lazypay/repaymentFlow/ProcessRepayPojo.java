package pojos.lazypay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessRepayPojo {

    public String txnId;
    public String txnStatus;
    public String amount ;

    //Bad Request Objects
    public String timestamp;
    public String status;
    public String error ;
    public String message;
    public String path;
    public String errorCode ;



}
