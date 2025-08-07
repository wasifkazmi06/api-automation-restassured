package pojos.billpayment.billFetch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomerParam {
    public String dataType;
    public boolean optional;
    public String paramName;
    public int minLength;
    public int maxLength;
    public Object regex;
    public Object values;
    public Object requiredIn;
    @JsonProperty("Vehicle_Registration_Number")
    public String vehicle_Registration_Number;
}
