package pojos.vaultTM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanDisassociatePojo {

    public String id;
    public String plan_id;
    public String job_id;
    public String status;
    public Object disassociate_account_update;
    public String create_timestamp;
    public String last_status_update_timestamp;
    public String failure_reason;

}

