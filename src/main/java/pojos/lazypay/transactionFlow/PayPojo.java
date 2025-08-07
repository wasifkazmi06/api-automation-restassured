package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PayPojo {

    public String transactionId;
    public String merchantOrderId;
    public String amount;
    public String currency;
    public String signature;
    public Map<String, Object> responseData;
    public Map<String, Object> token;
    public Map<String, Object> customParams;
    public List<Object> productSkuDetails;
    public Map<String, Object> userDetails;
    public String chat_bot_token;
    public String chat_bot_token_expires_in;

    //For Error
    public String message;
    public String timestamp;
    public String status;
    public String error;
    public String errorCode;
    public String path;
    public String attemptsRemaining;
    public String requestId;
    public String availableCreditLimit;
    public String error_description;
}


