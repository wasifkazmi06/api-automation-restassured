package pojos.billpayment.fetchBillAndLastBillDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserlinkedBill {
    public int id;
    public String uniqueId;
    public int userId;
    public String billerId;
    public String refId;
    public CustomerParams customerParams;
    public double lastBillAmount;
    public Date lastBillPaidDate;
    public boolean deleted;
    public String billCategory;
    public boolean isDue;
}
