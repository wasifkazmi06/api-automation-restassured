package pojos.cof.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class AddressPojo {
    private String line1;
    private String line2;
    private String pincode;
}
