package pojos.kyc.loginEssentials;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class LoginPojo {
    public String userId;
    public String umUuid;
    public Object username;
    public Object citrusId;
    public Object gender;
    public Object dob;
    public PrimaryMobilePojo primaryMobile;
    public Object secondaryMobiles;
    public Object primaryEmail;
    public Object secondaryEmails;
    public Object kycDetails;
    public Object profilePictureUrl;
    public String lpUserStatus;
    public String clUserStatus;
    public String walletUserStatus;
    public String upiUserStatus;
    public Object devices;
    public String appUserStatus;
    public String lpUuid;
    public Object walletId;
    public Object firstName;
    public Object lastName;
    public List<Object> userAddress;
    public Object socialProfiles;
    public Date dateCreated;
    public Date dateUpdated;
    public String source;
    public boolean umVerified;
    public boolean clEnabled;
    public int version;
    public boolean status;
    public String otpType;
    public String accessToken;
    public OauthTokenResponse oauthTokenResponse;
    public Object fullName;
    public boolean collectionSetupCompleted;
    public boolean kycCompleted;
    public Object employmentType;
}
