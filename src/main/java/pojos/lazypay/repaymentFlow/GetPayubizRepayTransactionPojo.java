package pojos.lazypay.repaymentFlow;

import java.util.Date;

public class GetPayubizRepayTransactionPojo {

    public String id;
    public int amount;
    public String type;
    public String status;
    public String paymentMode;
    public Date dateCreated;
    public Date dateUpdated;
    public int merchantId;
    public String merchantName;
    public String logoPath;
    public boolean isDebit;
    public String loanTxnId;
    public String merchantLabel;
    public String transactionTrackFlag;
}
