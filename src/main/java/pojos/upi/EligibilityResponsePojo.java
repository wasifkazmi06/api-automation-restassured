package pojos.upi;

import java.util.List;

public class EligibilityResponsePojo {
    public Boolean bnplEligible;
    public String reasonCode;
    public String errorCode;
    public List<CLPlanPojo> clPlans;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
}
