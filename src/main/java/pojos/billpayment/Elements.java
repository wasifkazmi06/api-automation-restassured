package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Elements {
    public int id;
    public String uniqueId;
    public int userId;
    public String billerId;
    public String refId;
    public String billerName;
    public boolean isAdhoc;
    public String fetchOption;
    public String paymentAmountExactness;
    public UserbillsCustomerParams userbillsCustomerParams;
    public double lastBillAmount;
    public Date lastBillPaidDate;
    public String flowType;
    public String billDueOverDueText;
    public boolean deleted;
    public String primaryCustomerParam;
    public String billCategory;
    public boolean isDue;

}
