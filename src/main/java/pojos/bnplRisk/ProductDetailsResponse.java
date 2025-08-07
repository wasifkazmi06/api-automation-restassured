package pojos.bnplRisk;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ProductDetailsResponse {

    public double limit;
    public String reason;
    public boolean decision;
    public List<InterestRateDetails> interestRates;
    public boolean is2FARequired;
}
