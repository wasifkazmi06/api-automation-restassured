package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashbackPojo {

    public String reason;
    public String status;

    /**
     * For error
     */
    public String timestamp;
    public String error;
    public String message;
    public String path;
    public String errorCode;

}
