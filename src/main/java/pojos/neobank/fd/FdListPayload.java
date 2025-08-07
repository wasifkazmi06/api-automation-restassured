package pojos.neobank.fd;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class FdListPayload {
    public ArrayList<FdList> fdList;
    public ArrayList<String> upiList;
}
