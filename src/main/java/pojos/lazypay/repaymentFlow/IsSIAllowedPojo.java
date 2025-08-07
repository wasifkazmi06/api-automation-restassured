package pojos.lazypay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsSIAllowedPojo {

    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
}
