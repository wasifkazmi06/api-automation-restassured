package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RiskPLUpdatePreApproveLimitPojo {

    public List<ProductLimitResponse> productLimits;
}
