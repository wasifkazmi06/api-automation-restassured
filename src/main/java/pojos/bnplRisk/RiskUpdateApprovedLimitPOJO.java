package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class RiskUpdateApprovedLimitPOJO {

    public Map<Product, ProductLimitResponse> productLimits;
}
