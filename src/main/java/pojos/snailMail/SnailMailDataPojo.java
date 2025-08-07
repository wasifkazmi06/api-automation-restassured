package pojos.snailMail;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class SnailMailDataPojo {
    //pull/push data json
    private String userName;
    private String userAddress;
    private Date agreementSignDate;
    private String product;
    private String mobile;
    private String apvId;
    private String status;
    private String courierNo;

    //apv_status data json
    private String userId;
    private int kycCaseId;
    private String kycStatus;
    private String kycState;
    private String kycTypeId;
    private int kycVersion;
    private String message;
    private String kycStatusMessage;
    private String kycCategory;
    private ArrayList<String> products;
    private String lastUpdated;
    private String dateCreated;
    private String kycApvStatus;
    private String  kycApvType;
    private String kycApvSource;
}