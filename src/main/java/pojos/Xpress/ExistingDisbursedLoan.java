package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExistingDisbursedLoan {
    @JsonProperty("loan_id")
    public int loan_id;
    @JsonProperty("loan_type")
    public String loan_type;
    @JsonProperty("tradelines")
    public ArrayList<Object> tradelines;
    @JsonProperty("loan_tenure")
    public int loan_tenure;
    @JsonProperty("disbursed_at")
    public Date disbursed_at;
    @JsonProperty("ams_reference")
    public Object ams_reference;
    @JsonProperty("interest_rate")
    public double interest_rate;
    @JsonProperty("assessment_lead_id")
    public String assessment_lead_id;
    @JsonProperty("assessment_request_id")
    public String assessment_request_id;
}
