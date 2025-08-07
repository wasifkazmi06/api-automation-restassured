package pojos.Xpress;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashLoanOfferingResponsePojo {
    public boolean qualifiedToAvailLoans;
    public String nextAction;
    public String creditLimit;
    public Double dueAmount;
    public String productOffering;
    public String panNumber;
    public String phoneNumber;
    public String loanCardImageKeyword;
    public String qualifyingCriteria;
    public boolean qualifiedByWhitelist;
    public boolean bnplOrganic;
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
