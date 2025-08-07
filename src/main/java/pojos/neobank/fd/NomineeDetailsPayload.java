package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NomineeDetailsPayload {
    public String name;
    public long dateOfBirth;
    public String relationship;

// for create Order:
    public String amount;
    public String dob;

    public CreateOrderPayload payload;
    public String status;

}
