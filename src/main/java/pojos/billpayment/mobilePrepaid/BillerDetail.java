package pojos.billpayment.mobilePrepaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class BillerDetail {
    public String billerId;
    public String billerName;
    public Object billerDescription;
    public String category;
    public String region;
    public String regionCode;
    public Object state;
    public String flowType;
    public String status;
    public ArrayList<CustomerParam> customerParams;
    public boolean reqParamGroups;
    public ArrayList<Object> customerParamGroups;
    public Object planMdmRequirement;
    public String supportBillValidation;
    public String fetchOption;
    public boolean fetchAllowed;
    public Object paymentAmountExactness;
    public String billerMode;
    public Object billPeriod;
    public boolean isActive;
    public boolean isAdhoc;
    public String displayParamName;
}
