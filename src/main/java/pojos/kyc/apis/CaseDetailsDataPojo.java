package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseDetailsDataPojo {
    public String name;
    public String dob;
    public String address;
    public String pincode;
    public String gender;
    public String profilePictureUrl;
    public String kycStatus;
    public boolean active;
    public String currentAddress;
    public String currentPincode;
    public boolean panVerified;
    public String employmentStatus;
    public String maskedPan;
    public boolean fatherNameAvailable;
    public String fatherOrSpouseName;
    public String motherName;
    public String poiType;
    public String poaType;
    public String nationality;
    public String panNumber;
    public String firstName;
    public String lastName;
    public String fullName;
    public String bureauConsent;
    public String ckycConsent;
    public String kycVersion;
}
