package pojos.billpayment.billpay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalParams {
    public String billerReferenceNumber;
    public String txnReferenceId;
    @JsonProperty("FASTAG Balance")
    public String fASTAG_Balance;
    @JsonProperty("Maximum_Allowable_Payment")
    public String maximum_Allowable_Payment;
}
