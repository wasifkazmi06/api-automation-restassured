package pojos.upi.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationStatusPojo {

    public String upiStatus;
    public String vpa;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;


}
