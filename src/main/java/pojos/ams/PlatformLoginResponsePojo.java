package pojos.ams;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlatformLoginResponsePojo {

    public String id;
    public String userId;
    public String umUuid;
    public Object username;
    public Object citrusId;
    public Object gender;
    public Dob dob;
    public PrimaryMobile primaryMobile;
    public Object secondaryMobiles;
    public PrimaryEmail primaryEmail;
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
    public String firstName;
    public Object lastName;
    public List<Object> userAddress = null;
    public Object socialProfiles;
    public String dateCreated;
    public String dateUpdated;
    public String source;
    public Boolean umVerified;
    public Boolean clEnabled;
    public Integer version;
    //public Boolean status;
    public String otpType;
    public String accessToken;
    public OauthTokenResponse oauthTokenResponse;
    public FullName fullName;
    public Boolean collectionSetupCompleted;
    public Boolean kycCompleted;
    public Object employmentType;
    public Object otpId;

    public String chat_bot_token;
    public String chat_bot_token_expires_in;


    //Error scenarios
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;


}
