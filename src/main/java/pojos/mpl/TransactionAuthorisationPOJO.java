package pojos.mpl;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class TransactionAuthorisationPOJO {

    public String transactionId;
    public String status;
    public String amount;
    public String message;
    public Map<String, Object> customParams;

    public Long timestamp;
    public String error;
    public String path;
    public String errorCode;
}

