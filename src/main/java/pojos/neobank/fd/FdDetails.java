package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FdDetails {
    public String fdId;
    public String entityId;

    public FdGenericPayload payload;
    public String status;
    public String message;


}
