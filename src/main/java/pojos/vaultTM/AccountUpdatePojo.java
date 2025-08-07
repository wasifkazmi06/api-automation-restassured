package pojos.vaultTM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdatePojo {

    public String id;
    public String account_id;
    public String status;
    public Object product_version_update;
    public String create_timestamp;
    public String last_status_update_timestamp;
    public String account_update_batch_id;
    public String job_id;
    public String failure_reason;

}
