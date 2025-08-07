package pojos.vaultTM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalAccountPojo {

    public String id;
    public String name;
    public String product_id;
    public String product_version_id;
    public Object permitted_denominations;
    public String status;
    public String opening_timestamp;
    public DetailsPojo details;
    public Object accounting;

}

