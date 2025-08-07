package pojos.neobank.support.transcation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    public String amount;
    public double balance;
    public String transactionType;
    public String type;
    public Object time;
    public int txRef;
    public Object businessId;
    public String beneficiaryName;
    public Object beneficiaryType;
    public String beneficiaryId;
    public String description;
    public String otherPartyName;
    public String otherPartyId;
    public String txnOrigin;
    public String transactionStatus;
    public Object status;
    public String yourWallet;
    public String beneficiaryWallet;
    public String externalTransactionId;
    public Object retrivalReferenceNo;
    public Object authCode;
    public Object billRefNo;
    public String bankTid;
    public Object acquirerId;
    public Object mcc;
    public Object convertedAmount;
    public Object networkType;
    public Object limitCurrencyCode;
    public Object kitNo;
    public Object sorTxnId;
    public Object transactionCurrencyCode;
    public Object fxConvDetails;
    public Object convDetails;
    public Object disputedDto;
    public Object disputeRef;
    public Object accountNo;
}
