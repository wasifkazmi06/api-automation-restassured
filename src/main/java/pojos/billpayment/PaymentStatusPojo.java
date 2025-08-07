package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import pojos.billpayment.billpay.AdditionalParams;
import pojos.billpayment.billpay.PaymentDetails;
import pojos.billpayment.fetchBillAndLastBillDetails.CustomerParams;

import java.util.Date;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentStatusPojo {
    public double paidAmount;
    public String refId;
    public String secureAppTxnId;
    public String message;
    public String billerId;
    public String billerName;
    public AdditionalParams additionalParams;
    public CustomerParams customerParams;
    public Date timeStamp;
    public String accountHolderName;
    public PaymentDetails paymentDetails;
    public String billerCategory;
    public String billerRegion;
    public String status;
}
