package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OnboardingCaseStatusResponsePojo {


    public String message;
    public String nextStep;
    public String nextAction;
    public Double availableCreditLimit;
    public Double additionalProposedLimit;
    public Double totalCreditLimit;
    public boolean clEnabled;
    public String lastCompletedCheckpoint;
    public String waitingForStep;
    public Double preApprovedLimit;
    public Double loanAmount;
    public String productOffering;
    public String lastCoveredApplicableCheckpoint;
    public String nextJourneyStep;
    public ArrayList<Integer> coveredCheckpoints;
    public String timestamp;
    public Double status;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
    public String maskedErrorCode;
    public String errorDetails;
    public String remarks;
}
