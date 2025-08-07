package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class FdList {
    public int fdId;
    public int investedAmount;
    public long createdAt;
    public String status;

    //for fd count
    public int payload;

    // for upi list
    public ArrayList<String> upiList;
}
