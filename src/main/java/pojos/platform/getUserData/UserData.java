package pojos.platform.getUserData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    private String id;
    private String umUuid;
    private Object username;
    private Object citrusId;
    private Gender gender;
    private DOB dob;
    private PrimaryMobile primaryMobile;
    private Object secondaryMobiles;
    private PrimaryEmail primaryEmail;
    private Object secondaryEmails;
    private Object kycDetails;
    private String profilePictureUrl;
    private String lpUserStatus;
    private String clUserStatus;
    private String walletUserStatus;
    private String upiUserStatus;
    private Object devices;
    private String appUserStatus;
    private String lpUuid;
    private Object walletId;
    private String firstName;
    private Object lastName;
    private List<UserAddress> userAddress = new ArrayList<UserAddress>();
    private Object socialProfiles;
    private String dateCreated;
    private String dateUpdated;
    private String source;
    private Boolean umVerified;
    private Boolean clEnabled;
    private FullName fullName;
    private Boolean collectionSetupCompleted;
    private Boolean kycCompleted;
    private Object employmentType;
    private List<UserTnC> userTnCs = new ArrayList<UserTnC>();
    private Integer version;
    private Object repaymentCycle;
    private Consents consents;
    private Object kfsDetails;
    private String userConsent;
    private Object agreements;
    private Object eligibleProducts;
    private UserD30Details userD30Details;

    /**
     * For error
     */
    public String timestamp;
    public String error;
    public String message;
    public String path;
    public String status;

}
