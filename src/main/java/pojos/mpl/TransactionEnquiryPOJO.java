package pojos.mpl;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
public class TransactionEnquiryPOJO {

    public String merchantTxnId;
    public ArrayList<Object> transactions;

    public Long timestamp;
    public int status;
    public String error;
    public String path;
    public String errorCode;
    public String message;
}

