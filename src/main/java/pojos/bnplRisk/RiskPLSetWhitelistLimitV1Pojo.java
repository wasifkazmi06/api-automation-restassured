package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RiskPLSetWhitelistLimitV1Pojo {

    public List<ProductLimitResponse> productLimits;
}
