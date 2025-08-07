package pojos.lazypay.transactionFlow;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOTPPojo {

    /**
     * For error
     */
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;

}
