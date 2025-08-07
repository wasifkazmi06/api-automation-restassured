package pojos.billpayment.billFetch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillFetchPojo {
    public double amount;
    public double amountToBePaid;
    public String refId;
    //public ArrayList<Object> amountDetails;
    public String accountHolderName;
    public HashMap<String,String> additionalParams;
    public BillerDetails billerDetails;
    public HashMap<String,String> customerParams;
    public String primaryCustomerParam;
    public String status;
    public String error;
    public String response;
    public String errorCode;
    public String message;
    public String path;
    public String source;
}


