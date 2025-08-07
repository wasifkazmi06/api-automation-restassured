package pojos.billpayment.mobilePrepaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class CustomerParam {
    public String dataType;
    public boolean optional;
    public String paramName;
    public int minLength;
    public int maxLength;
    public String regex;
    public Object values;
    public String requiredIn;
}
