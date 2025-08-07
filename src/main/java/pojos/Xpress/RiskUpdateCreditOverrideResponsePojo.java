package pojos.Xpress;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskUpdateCreditOverrideResponsePojo {
    public boolean decision;
    public double limit;
    public Double cycleLimit;
    public Double termLimit;
    public Double consumedTermLimit;
    public long flowIndicator;
    public long score;
    public String requestId;
    public String responseId;
    public String reasonCode;
    public boolean ruleApplied;
    public Object creditFlowIndicator;
    public Object userLimitFetchResponse;
    public Object is2FARequired;
    public String description;
}
