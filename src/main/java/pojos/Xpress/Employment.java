package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employment {
    public Object employer;
    public String industry;
    @JsonProperty("payment_mode")
    public String payment_mode;
    @JsonProperty("sub_industry")
    public String sub_industry;
    @JsonProperty("employer_name")
    public String employer_name;
    @JsonProperty("monthly_income")
    public int monthly_income;
    @JsonProperty("employment_type")
    public String employment_type;
    @JsonProperty("employer_category")
    public String employer_category;
    @JsonProperty("business_entity_type")
    public Object business_entity_type;
    @JsonProperty("is_employer_verified")
    public Object is_employer_verified;
    @JsonProperty("itr_income_latest_fy")
    public Object itr_income_latest_fy;
    @JsonProperty("business_vintage_years")
    public Object business_vintage_years;
    @JsonProperty("is_employment_verified")
    public Object is_employment_verified;
    @JsonProperty("itr_income_previous_fy")
    public Object itr_income_previous_fy;
    @JsonProperty("employer_verification_status")
    public String employer_verification_status;
}
