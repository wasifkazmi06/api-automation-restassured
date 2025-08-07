package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GetAppOTPPojo {

    public List<TransactionOtpInfoPojo> transactions;

    /**
     * For error
     */
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;

}
