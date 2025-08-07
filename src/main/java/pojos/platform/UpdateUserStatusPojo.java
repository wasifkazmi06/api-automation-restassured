package pojos.platform;

import lombok.Getter;
import lombok.Setter;
import pojos.lazypay.transactionFlow.ResponseDataOTP;


@Getter
@Setter
public class UpdateUserStatusPojo {

    //For Error
    public String error_description;
    public String error;
    public String status;
    public String timestamp;
    public String message;
    public String path;
}