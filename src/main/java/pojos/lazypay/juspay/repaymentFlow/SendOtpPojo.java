package pojos.lazypay.juspay.repaymentFlow;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendOtpPojo {
    public Object actions;
    public Meta meta;


    public int attemptsRemaining;
 //   public String message;
    public String path;
    public String status;
    public String error;
    public String timestamp;
    public String lpUserStatus;
    public String title;
    public String description;
    public String type;
    public String UPGRADE;
    public String errorCode;
    public String metaData;
    public String resendDelayInSeconds;

}

