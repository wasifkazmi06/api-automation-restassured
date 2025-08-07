package pojos.mpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updateRepayStatusPOJO {

    public String status;
    public String amount;
    public String merchantTxnId;

    public Long timestamp;
    public String error;
    public String path;
    public String errorCode;
    public String message;
}