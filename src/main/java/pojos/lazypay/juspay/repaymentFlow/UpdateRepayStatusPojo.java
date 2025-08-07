package pojos.lazypay.juspay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class UpdateRepayStatusPojo {

    public String transactionId;
    public Double amount;
    public String status;
    public String type;
    public String mobile;
    public Double availableCreditLimit;
    public String errorMessage;
    public String paymentMode;
    public Date txnCreatedDate;
    public long repaidAt;
    public String bankName;
    public String cardType;
    public String paidWithSavedCard;
    public String saveThisCard;
    public String vpa;
    public String packageName;
    public String pgName;
    public String gatewayRefId;
    public String amountDetails;
    public Date txnSuccessDate;
    public AutopayEnquiryResponsePojo autopayEnquiryResponse;
}
