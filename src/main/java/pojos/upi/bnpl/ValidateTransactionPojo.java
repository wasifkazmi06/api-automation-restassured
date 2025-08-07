package pojos.upi.bnpl;

import lombok.Getter;
import lombok.Setter;
import pojos.upi.AmountPojo;
import pojos.upi.convertToEMI.TenurePojo;

@Getter
@Setter
public class ValidateTransactionPojo {

    public String txnRefNo;
    public String nextInstallmentDate;
    public AmountPojo amount;
    public AmountPojo emiAmount;
    public String responseCode;
    public String merchantName;
    public String dueDate;
    public String vpa;
    public String upiResponseCode;
    public String upiApprovalRefNo;
    public String merchantTxnRefNo;
    public String interestRate;
    public String processingFee;
    public TenurePojo tenure;
    public String errorCode;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
}


