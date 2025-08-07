package pojos.mpl;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;

@Getter
@Setter
public class TransactionDetailsPOJO {

    public JSONArray transactionId;

    public Long timestamp;
    public int status;
    public String error;
    public String path;
    public String errorCode;
    public String message;
}

