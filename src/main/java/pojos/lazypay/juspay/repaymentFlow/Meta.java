package pojos.lazypay.juspay.repaymentFlow;

import lombok.Getter;
import lombok.Setter;

public class Meta {
    public String otpId;
    public String message;
    public int resendDelayInSeconds;
    public int attemptsRemaining;
}