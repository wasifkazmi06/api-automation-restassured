package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TransactionOtpInfoPojo {


    public String txnId;
    public String merchantName;
    public String merchantLogo;
    public Double amount;
    public Date dateCreated;
    public int txnExpiryInSec;
    public String otpId;
}
