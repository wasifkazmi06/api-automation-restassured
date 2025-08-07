package pojos.billpayment.billFetch;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import pojos.billpayment.fetchBillAndLastBillDetails.CustomerParams;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class BillerDetails {

    public String billerId;
    public String billerName;
    public Object billerDescription;
    public String category;
    public String region;
    public String regionCode;
    public String state;
    public String flowType;
    public String status;
    public ArrayList<CustomerParams> customerParams;
    public boolean reqParamGroups;
    public ArrayList<Object> customerParamGroups;
    public String planMdmRequirement;
    public String supportBillValidation;
    public String fetchOption;
    public boolean fetchAllowed;
    public String paymentAmountExactness;
    public String billerMode;
    public Object billPeriod;
    public Object displayParamName;
    public boolean isActive;
    public boolean isAdhoc;
}
