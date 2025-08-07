package pojos.lazypay.transactionFlow;


import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.List;

@Getter
@Setter
public class MerchantRefundPojo {

    public String status;
    public String respMessage;
    public String lpTxnId;
    public String txnDateTime;
    public Double amount;

    /**
     * For error
     */
    public String timestamp;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public List<FieldErrorsPojo> fieldErrors;

}
