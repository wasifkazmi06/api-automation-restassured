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
public class ServiceabilityMap {
    public boolean ksf;
    public Object rbl;
    public boolean idfc;
    public boolean iifl;
    public boolean payufin;
    public Object fullerton;
    @JsonProperty("payufin_10l")
    public boolean payufin_10l;
    @JsonProperty("northern_arc")
    public boolean northern_arc;
}
