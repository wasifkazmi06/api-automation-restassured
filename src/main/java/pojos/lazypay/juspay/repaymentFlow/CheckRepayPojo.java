package pojos.lazypay.juspay.repaymentFlow;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CheckRepayPojo {

    public  String transactionId;
    public  int amount;
    public  double availableCreditLimit;
    public  double repaidAt;
    public  double txnCreatedDate;
    public  String status;
    public  String type;
    public  String mobile;
    public  String errorMessage;
    public  String message;
    public  String paymentMode;
    public  String successRedirectUrl;
    public  String failureRedirectUrl;
    public  String paymentSuccessUrl;
    public  String paymentFailureUrl;
    public  String bankName;
    public  String cardType;
    public  String paidWithSavedCard;
    public  String saveThisCard;
    public  String vpa;
    public  String packageName;
    public  String pgName;
    public  String gatewayRefId;
    public  ObjectNode modalInfo;
    public JsonNode banners;
    public String amountDetails;
    public String timestamp;
    public String error;
    public String path;
    public String errorCode;

}
