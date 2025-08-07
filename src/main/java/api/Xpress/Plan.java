package api.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plan {
    public int duration;
    public double annualInterestRate;
    public String durationUnit;
    public double interestRate;
    public double baseInterestRate;
    public int maxLoanAmount;
    public int maxBaseLoanAmount;
    public int minLoanAmount;
}
