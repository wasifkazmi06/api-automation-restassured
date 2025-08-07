package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
    public class GetPanDetailsResponsePojo {

    public String panNumber;
    public String firstName;
    public String lastName;
    public String dob;
    public String gender;
    public String employmentStatus;
    public String monthlySalary;
    public String houseNumber;
    public String locality;
    public String pinCode;
    public String city;
    public String failureReason;
    public String productOffering;
    public boolean panDataCollectedComplete;
    public boolean kycSuccessful;
    public boolean panVerified;
    public boolean signUpUser;
    public String timestamp;
    public Double status;
    public String error;
    public String message;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String maskedErrorCode;
    public String source;
}
