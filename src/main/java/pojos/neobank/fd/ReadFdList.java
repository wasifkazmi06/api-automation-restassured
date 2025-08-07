package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadFdList {

    public String fdId;
    public FdListPayload payload;

    public String status;
}
