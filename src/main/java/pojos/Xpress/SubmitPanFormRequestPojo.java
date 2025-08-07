package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitPanFormRequestPojo {

    public String qualifiedToAvailLoans;
    public String firstName;
    public String lastName;
    public String dob;
    public String gender;
    public String mobile;
    public String email;
    public String panNumber;
    public String currentPinCode;
    public String employmentStatus;
    public String monthlySalary;
    public String source;
    public String reasonForLoan;
    public String bureauAndCkycConsent;
    public String nonMicroFinanceConsent;


    public String timestamp;
    public Double status;
    public String error;
    public String message;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String maskedErrorCode;
}
