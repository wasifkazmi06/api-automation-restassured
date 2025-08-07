package pojos.mpl;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class TransactionRefundPOJO {

    public String status;
    public String message;
    public String transactionId;
    public String amount;

    public Long timestamp;
    public String error;
    public String path;
    public String errorCode;
}
