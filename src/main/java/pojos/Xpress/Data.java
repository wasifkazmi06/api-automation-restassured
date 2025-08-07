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
public class Data {
    @JsonProperty("user_data")
    public UserData userData;
    @JsonProperty("bureau_data")
    public Object bureau_data;
    @JsonProperty("tradeline_ids")
    public Object tradeline_ids;
    @JsonProperty("application_data")
    public ApplicationData applicationData;
    @JsonProperty("underwriting_data")
    public Object underwriting_data;
    @JsonProperty("lender_costs_info_dict")
    public Object lender_costs_info_dict;
}
