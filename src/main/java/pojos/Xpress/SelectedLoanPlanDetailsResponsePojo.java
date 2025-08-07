package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectedLoanPlanDetailsResponsePojo {

    public String loanApplicationId;
    public String status;
    public Loan loan;
    public ProcessingFee processingFee;
    public InsuranceDetails insuranceDetails;
    public String manualOpsDecision;
    public boolean loanPlanSelected;
    public boolean applicationApproved;
    public boolean manualOpsDecisionReady;
    public boolean manualOpsRequired;
    public boolean rejected;
    public boolean applicationSubmitted;
    public boolean applicationPreApproved;
    public String timestamp;
    public String error;
    public String path;
    public String errorId;
    public String errorCode;
    public String reason;
}
