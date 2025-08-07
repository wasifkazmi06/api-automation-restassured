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
public class Address {
    public int id;
    public String city;
    public String line1;
    public String line2;
    public String state;
    @JsonProperty("postal_code")
    public String postal_code;
    @JsonProperty("address_subtype")
    public Object address_subtype;
    @JsonProperty("serviceability_map")
    public ServiceabilityMap serviceability_map;
    @JsonProperty("geo_pincode_to_user_pincode_distance")
    public double geo_pincode_to_user_pincode_distance;
    @JsonProperty("comm_pin_code_out_of_serviceable_area")
    public Object comm_pin_code_out_of_serviceable_area;
    @JsonProperty("is_kyc_pincodes_contains_user_pincode")
    public boolean is_kyc_pincodes_contains_user_pincode;
}
