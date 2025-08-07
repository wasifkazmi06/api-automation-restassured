package pojos.billpayment.mobilePrepaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepaidRechargePlansPojo {
    public Biller biller;

    public ArrayList<Plan> plans;
    public int status;
    public String error;
    public String errorCode;
    public String message;
    public String path;
    public String source;
}
