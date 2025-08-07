package pojos.neobank.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizetransactionPojo {

    public String txnRefNo;
    public String status;
    public String description;
    public String settlementDate;
    public String currencyCode;
    public Object errorCode;
    public String balance;
}
