package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class bbpsCallBackApiRequestPojo {
    public String camp_id;
    public String ivr_id;
    public String mobile;
    public String pickup_Time;
    public String hangup_Time;
    public String status;
    public String reason;
    public String duration;
    public String keyPress;
}
