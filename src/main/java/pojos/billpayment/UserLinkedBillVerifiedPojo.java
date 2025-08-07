package pojos.billpayment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserLinkedBillVerifiedPojo {
    public int userLinkedBillId;
    public String message;
    public int status;
    public String error;
    public String errorCode;
    public String path;
    public String source;
}
