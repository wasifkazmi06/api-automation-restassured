package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    public boolean decision;
    public String ruleApplied;
    public String reasonCode;
    public boolean is2FARequired;
}
