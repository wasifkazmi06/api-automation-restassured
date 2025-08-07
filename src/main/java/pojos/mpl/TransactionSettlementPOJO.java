package pojos.mpl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSettlementPOJO {

    public String status;
    public String message;

    public Long timestamp;
    public String error;
    public String path;
    public String errorCode;
}
