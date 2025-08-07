package pojos.lazypay.juspay.repaymentFlow;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepayDetailsPojo {

    public String identifier;
    public String email;
    public String mobile;
    public double totalOutstanding;
    public double lastBilledAmount;
    public double dueAmount;
    public double totalLateFeeAmount;
    public double madAmount;
    public String signature;
    public double madInterestRate;
    public int isClEnabled;
    public int isRevolveEligible;
    public int showSavedCCBannerForRevolveUser;
    public boolean dueDatePassed;
    public int isMADOptedInCurrentCycle;
    public int isUPIIntentAllowed;
    public String dueDate;
    public String nextDueDate;

    public String lastCycleStartDate;
    public String lastCycleEndDate;
    public String dueSince;
    public String nextInterestChargeDate;
    public String nachExecutionDate;
    public String nachSetupDetails;
    public List<String> payModeDisplayOrder;
    public String appLastCycleStartDate;
    public String appLastCycleEndDate;
    public String appDueSince;
    public String appDueDate;
    public boolean ccrepaymentEligible;
    public int isPartialRepaymentEligible;
    public boolean nachSetupActive;
    public String interestAmount;
    public String bnplDueAmount;
    public String loanAmount;
    public String lateFeeAmount;
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
    public JsonNode revolveOs;
    public String mobileNo;
    public boolean delinquentUser;
    public float payableOs;

    //public List<ErrorCodePojo> error;
}
