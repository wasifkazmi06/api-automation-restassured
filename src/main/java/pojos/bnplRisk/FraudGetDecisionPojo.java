package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class FraudGetDecisionPojo {

    public Integer decision;
    public Integer flowIndicator;
    public String requestId;
    public String responseId;
    public String ruleApplied;
    public String reasonCode;
    public String description;
    public Boolean is2FARequired;
    public Map<Product, ProductResponse> productResponseMap;
    public Map<String, Object> metaData;

}