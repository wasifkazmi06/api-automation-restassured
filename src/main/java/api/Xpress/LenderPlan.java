package api.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LenderPlan {
    public String lender;
    public boolean isApproved;
    public Object isEligible;
    public int creditLine;
    public Object isCmpEligible;
    public Object monthlyCapacity;
    public String approvalMethod;
    public ArrayList<Plan> plans;
    public int maxTenure;
    public Object foir;
    public Object processingFeeCategory;
    public Object tradelineId;
    public Object monthlyCapacitySource;
    public Object decision;
    public Object reason;
    public Object approvedLine;
    public Object availableCreditLine;
}
