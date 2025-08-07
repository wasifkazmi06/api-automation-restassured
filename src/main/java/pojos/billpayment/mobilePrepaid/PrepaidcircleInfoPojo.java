package pojos.billpayment.mobilePrepaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrepaidcircleInfoPojo {
    public String circleName;
    public String circleRefId;
    public int status;
    public String error;
    public String errorCode;
    public String message;
    public String path;
    public String source;


}
