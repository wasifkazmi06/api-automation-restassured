package pojos.billpayment.billpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillpayPojo {
    public double paidAmount;
    public String refId;
    public String secureAppTxnId;
    public String message;
    public String billerId;
    public String billerName;
    public AdditionalParams additionalParams;
    public HashMap<String,String> customerParams;
    public Date timeStamp;
    public String accountHolderName;
    public PaymentDetails paymentDetails;
    public String billerCategory;
    public String billerRegion;
    public String status;
    public String errorCode;
    public String error;
    public String path;
    public String source;

}
