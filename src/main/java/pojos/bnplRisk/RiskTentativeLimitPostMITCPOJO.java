package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RiskTentativeLimitPostMITCPOJO {

    public String mobile;
    public Double limit;
    public Double cycleLimit;
    public Double termLimit;
    public int riskCategory;

}
