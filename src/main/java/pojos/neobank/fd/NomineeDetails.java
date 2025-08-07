package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NomineeDetails {

    public String amount;
    public String name;
    public String relationship;
    public String dob;

    public CreateOrderPayload payload;
    public String status;


}
