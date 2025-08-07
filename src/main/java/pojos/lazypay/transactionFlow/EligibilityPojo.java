package pojos.lazypay.transactionFlow;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EligibilityPojo {

    public Boolean txnEligibility;
    public String reason;
    public String code;
    public Boolean userEligibility;
    public List<String> signUpModes;
    public Boolean autoDebit;
    public Map<String, Object> customParams;
    public String merchantLogo;
    public String eligibilityResponseId;
    public Boolean emailRequired;
    public Boolean afarequired;


    //For v2
    public Boolean addressReq;
    public Boolean firstNameReq;
    public Boolean lastNameReq;
    public String dueDate;
    public Boolean repayConfirmation;

    //For v4
    public Double billedAmount;
    public Double convenienceFee;

    //For v5
    public Double availableCreditLimit;

    //For v6
    public Boolean existingUser;

    //For Error
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public String requestId;


}
