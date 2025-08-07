package pojos.lazypay.juspay.repaymentFlow;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundEnquiryPojo {

    public String errorMessage;
    public double amount;
    public String status;
    public String transactionId;
    public String type;
}
