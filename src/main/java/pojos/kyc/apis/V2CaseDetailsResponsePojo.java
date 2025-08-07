package pojos.kyc.apis;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class V2CaseDetailsResponsePojo {
    public int id;
    public String name;
    public String dob;
    public String address;
    public String pincode;
    public String gender;
    public long dateCreated;
    public long dateUpdated;
    public String profilePictureUrl;
    public String product;
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
    public String location;

    // error response
    public long timestamp;
    public int status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public Object metadata;

}
