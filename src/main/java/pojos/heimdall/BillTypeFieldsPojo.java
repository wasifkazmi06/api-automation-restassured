package pojos.heimdall;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class BillTypeFieldsPojo {

    private String type;
    private List<String> serviceproviders;
    private List<String> fields;

}
