package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AutoDebitPayMIPojo {

    public String transactionId;
    public String merchantTxnId;
    public String status;
    //For Error
    public String message;
    public String timestamp;
    public String error;
    public String errorCode;
    public String path;
    public String attemptsRemaining;
    public String requestId;
    public String availableCreditLimit;
    public String error_description;
}


