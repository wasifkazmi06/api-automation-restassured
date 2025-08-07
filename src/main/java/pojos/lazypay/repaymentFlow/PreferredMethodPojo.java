package pojos.lazypay.repaymentFlow;


import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PreferredMethodPojo {

    public String paymentMethod;
    public String bankCode;
    public String userIdentifier;
    public CardDetailsPojo card;
    public String vpa;
    public String bankName;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;

}
