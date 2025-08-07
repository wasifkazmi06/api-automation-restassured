package pojos.lazypay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class InitiateRepayPojo {

    public String transactionId;
    public String amount;
    public String phone;
    public String pgUsed;
    public String firstName;
    public String lastName;
    public String email ;
    public String surl;
    public String curl;
    public String furl;
    public String key;
    public String hash;
    public String productInfo;
    public String pg;
    public String bankCode;
    public String ccNum;
    public String ccName;
    public String ccvv;
    public String ccExpMon;
    public String ccExpYr;
    public String userCredentials;
    public String saveThisCard;
    public String cardToken;
    public String deviceType;
    public String subscribeThisCard;
    public String paymentUrl;
    public String vpa;

    //Bad Request Objects
    public String timestamp;
    public String status;
    public String error ;
    public String message;
    public String path;
    public String errorCode ;
}
