package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillerDetailsPojo {
    public String billerId;
    public String billerName;
    public Object billerDescription;
    public String category;
    public String region;
    public String regionCode;
    public String state;
    public String flowType;
    public String status;
    public String response;
    public ArrayList<BilldetailCustomerparam> BilldetailCustomerparam;
    public boolean reqParamGroups;
    public ArrayList<Object> customerParamGroups;
    public String planMdmRequirement;
    public String supportBillValidation;
    public String fetchOption;
    public boolean fetchAllowed;
    public String paymentAmountExactness;
    public String billerMode;
    public int billPeriod;
    public Object displayParamName;
    public boolean isActive;
    public boolean isAdhoc;
    //public ArrayList<allowedPaymentMode> allowedPaymentMode;


}
