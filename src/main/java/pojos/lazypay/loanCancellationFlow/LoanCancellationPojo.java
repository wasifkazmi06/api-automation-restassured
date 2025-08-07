package pojos.lazypay.loanCancellationFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanCancellationPojo {

    public String settlementAmount;
    public String cancellationStatus;
    public String loanRefundTxnId;
    public String loanSettlementTxnId;
    public String loanRefundAmount;
    public String loanEmiTxnIds;

    //For Error
    public String timestamp;
    public String status;
    public String error;
    public String errorCode;
    public String exception;
    public String message;
    public String path;
    public String statusCode;
    public String reason;
    public String errorDescription;


}
