package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EligibilityResponseList {
    public String mobile;
    public String uuid;
    public String status;
    public boolean decision;
    public Object limit;
    public boolean bnplDecision;
    public Object bnplLimit;
    public boolean lazycardDecision;
    public Object lazycardLimit;
}
