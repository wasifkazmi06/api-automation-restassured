package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class RiskGetProductLimitPojo {

    public Map<Product, ProductDetailsResponse> productLimitDetails;
    public Integer decision;
    public Float limit;
    public Float cycleLimit;
    public Float termLimit;
    public Float consumedTermLimit;
    public Integer flowIndicator;
    public Integer score;
    public String requestId;
    public String responseId;
    public String reasonCode;
    public String description;
    public Map<String, Object> ruleApplied;
    public Integer creditFlowIndicator;
    public Integer creditDecision;
    public UserLimitFetchResponse userLimitFetchResponse;
    public Boolean is2FARequired;
    public Map<Product, ProductDetailsResponse> productDetails;
    public Map<String, Object> metaData;
    public String maxActiveLoans;
}
