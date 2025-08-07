package pojos.kyc.loginEssentials;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProfileDataPojo {
    public String email;
    public int emailVerified;
    public Object emailVerifiedDate;
    public String mobile;
    public int mobileVerified;
    public long mobileVerifiedDate;
    public String firstName;
    public String lastName;
    public String uuid;
}
