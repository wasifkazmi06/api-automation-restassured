package pojos.vaultTM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAccountPojo {

    public String id;
    public String name;
    public String product_id;
    public String product_version_id;
    public String status;
    public String statusCode;
    public String message;
    public Object data;
}
