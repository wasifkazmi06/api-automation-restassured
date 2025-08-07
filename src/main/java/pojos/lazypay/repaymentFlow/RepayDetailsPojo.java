package pojos.lazypay.repaymentFlow;

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
    public double dueAmount;
    public double totalLateFeeAmount;
    public double madAmount;
    public double madInterestRate;
    public int isClEnabled;
    public int isRevolveEligible;
    public int showSavedCCBannerForRevolveUser;
    public boolean dueDatePassed;
    public int isMADOptedInCurrentCycle;
    public String dueDate;
    public String nextDueDate;
    public String lastCycleStartDate;
    public String lastCycleEndDate;
    public String dueSince;
    public String nextInterestChargeDate;
    public String nachExecutionDate;
    public Object nachSetupDetails;
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
    public String revolveOs;
    public String foreclosureAmount;
    public boolean ccrepaymentEligible;
    public String maxLoanConversionDue;
    public boolean delinquentUser;
    public String lpOverdueCharges;
    public Double payableOs;
    public String autopayStatus;
    public UPISetupDetailsPojo upiSetupDetails;
    public Double dpdCount;
    public int s2EmiLoanCount;
    public Object creditTxns;
    public Object lastSelectedLoanPlan;
    //public List<ErrorCodePojo> error;

}
