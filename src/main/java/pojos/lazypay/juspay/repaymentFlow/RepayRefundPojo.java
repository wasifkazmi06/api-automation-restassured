package pojos.lazypay.juspay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepayRefundPojo {
    public String amount;
    public String lpTxnId;
    public String respMessage;
    public String status;
    public String txnDateTime;
    public double timestamp;
    public String error;
    public String message;
    public String path ;
    public String errorCode;
    public String parentTxnId;


}
