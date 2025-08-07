package pojos.billpayment.mobilePrepaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Plan {
    public Object planId;
    public String planName;
    public String price;
    public String validity;
    public String talkTime;
    public String data;
    public String validityDescription;
    public String packageDescription;
    public String planType;
}
