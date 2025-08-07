package pojos.billpayment.fetchBillAndLastBillDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerDetails {
    public String billerId;
    public String billerName;
    public String billerDescription;
    public String category;
    public String region;
    public String regionCode;
    public String state;
    public String flowType;
    public String status;
    public ArrayList<CustomerParams> customerParams;
    public boolean reqParamGroups;
    public ArrayList<CustomerParamGroup> customerParamGroups;
    public String planMdmRequirement;
    public String supportBillValidation;
    public String fetchOption;
    public boolean fetchAllowed;
    public String paymentAmountExactness;
    public String billerMode;
    public Object billPeriod;
    public Object primaryCustomerParam;
    public boolean isActive;
    public boolean isAdhoc;
}
