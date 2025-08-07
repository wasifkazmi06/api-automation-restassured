package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class InitiatePojo {

    public String txnRefNo;
    public List<String> paymentModes;
    public String lpTxnId;
    public Map<String, Object> responseData;
    public String checkoutPageUrl;
    public Double billedAmount;
    public Double convenienceFee;
    public Map<String, Object> customParams;
    public String dueDate;
    public boolean repayConfirmation;
    /**
     * Error
     */
    public String status;
    public String timestamp;
    public String message;
    public String error;
    public String path;
    public String errorCode;

    
}