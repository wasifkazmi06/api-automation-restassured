package pojos.billpayment.fetchBillAndLastBillDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLinkedBillIdPojo {
    public Object fetchBillResponse;
    public Object prepaidPlanInfo;
    public BillerDetails billerDetails;
    public UserlinkedBill userlinkedBill;
    public String message;
    public String errorCode;


}
