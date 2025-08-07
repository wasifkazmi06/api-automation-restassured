package pojos.lazypay.juspay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutopayEnquiryResponsePojo {
    
    public String startDate;
    public boolean revokableByCustomer;
    public Double maxAmount;
    public String mandateType;
    public String mandateToken;
    public String mandateStatus;
    public String mandateId;
    public String frequency;
    public String endDate;
}
