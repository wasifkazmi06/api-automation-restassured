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
public class ApplicationData {
    public Object lenders;
    public String product;
    @JsonProperty("ten_lakh")
    public Object ten_lakh;
    @JsonProperty("lps_location")
    public Object lps_location;
    @JsonProperty("assign_single_lender")
    public Object assign_single_lender;
    @JsonProperty("bureau_surrogate_data")
    public Object bureau_surrogate_data;
    @JsonProperty("application_status_next")
    public Object application_status_next;
    @JsonProperty("application_status_current")
    public String application_status_current;
}
