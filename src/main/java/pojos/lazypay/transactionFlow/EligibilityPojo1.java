package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EligibilityPojo1 {

    public String txnEligibility;
    public String reason;
    public String code;
    public String userEligibility;
    public Object signUpModes;
    public String autoDebit;
    public Object customParams;
    public String merchantLogo;
    public String message;
    public String eligibilityResponseId;
    public String emailRequired;

    //For v2
    public String addressReq;
    public String firstNameReq;
    public String lastNameReq;
    public String dueDate;
    public String repayConfirmation;

    //For Error
    public String timestamp;
    public String status;
    public String error;
    public String path;
    public String errorCode;
}
