package pojos.paylaterOnboarding;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PLGetUserStatusPojo {

    public String status;
    public String userProfile;
    public boolean kycCompleted;
    public boolean mitcSigned;
    public boolean merchantTransactedLPUser;
}
