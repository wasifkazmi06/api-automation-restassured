package pojos.lazypay.userOnboardingFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOnboardingPojo {

    public String uuid;
    public String email;
    public String mobile;
    public String firstName;
    public String lastName;
    public String isEmailVerified;
    public String isMobileVerified;
    public String dateCreated;
    public String isTransacted;
    public String optedOut;
    public String isBlocked;
    public String blockReason;
    public String userMigrated;
    public String newUser;

    //error codes

    public String error;
    public String status;
    public String message;
    public String timestamp;
    public String exception;

}
