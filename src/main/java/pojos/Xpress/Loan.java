package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan {

    public Double amount;
    public Integer tenure;
    public TentativePreEmiInfo tentativePreEmiInfo;
    public Double emi;
    public String firstInstallmentDate;
    public ArrayList<Schedule> schedule;
    public Double totalInterest;
    public Double annualInterestRate;


}
