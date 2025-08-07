package pojos.mpl;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class EligibilityPOJO {

    public Boolean txnEligibility;
    public String reason;
    public String code;
    public Double availableLimit;
    public String eligibilityResponseId;
    public Map<String, Object> customParams;

    public Long timestamp;
    public int status;
    public String error;
    public String path;
    public String errorCode;
    public String message;
}
