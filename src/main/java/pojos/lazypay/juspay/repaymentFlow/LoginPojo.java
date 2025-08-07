package pojos.lazypay.juspay.repaymentFlow;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoginPojo {

    public String UPGRADE;
    public String title;
    public String appUser;
    public String clUser;
    public String clientType;
    public boolean continueCLOnboarding;
    public String dateCreated;
    public String dateUpdated;
    public String firstName;
    public boolean kycCompleted;
    public String identifier;
    public String lastName;
    public String lpUser;
    public String middleName;
    public String otpType;
    public String templateIntro;
    public String umUuid;
    public String source;
    public boolean showIntro;
    public boolean status;
    public String userId;
    public String userName;
    public String vpa;
    public String walletUser;
    public ObjectNode clinkToken;
    public ObjectNode dob;
    public ObjectNode fullName;
    public ObjectNode gender;
    public ObjectNode kycDetails;
    public ObjectNode productList;
    public List<String> mandatoryFields;
    public ObjectNode oauthTokenResponse;
    public ObjectNode primaryMobile;
    public ObjectNode primaryEmail;
    public ObjectNode secondaryEmails;
    public ObjectNode secondaryMobiles;
    public ObjectNode userAddress;
    public ObjectNode mandatoryPermission;
    public String description;
    public String type;
    public String timestamp;
    public String error;
    public String path;
    public String errorCode;
    public String message;
    public String dueDate;



}
