package pojos.lazypay.juspay.repaymentFlow;



import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
public class CreateOrderPojo {
    //public  ObjectNode lpTxnId;
    //public static ArrayList lpTxnId;

    public  String lpTxnStatus;
    public  String orderId;
    public  String clientAuthToken;
    public  String clientAuthTokenExpiry;
    public  String paymentUrl;
    public  String lpTxnId;
    public double timestamp;
    public  String error;
    public  String message;
    public  String path;
    public double status;
    public String errorCode;
    public  String upiRepaymentUri;







}
