package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Plans {
    public Integer planId;
    public Double minAmount;
    public Double maxAmount;
    public Integer tenure;
    public Double annualInterestRate;
}
