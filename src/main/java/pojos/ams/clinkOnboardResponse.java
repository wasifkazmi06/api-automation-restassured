package pojos.ams;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class clinkOnboardResponse {
    public Integer uuid;
    public String email;
    public String mobile;
    public Object firstName;
    public Object lastName;
    public Integer isEmailVerified;
    public Integer isMobileVerified;
    public Long dateCreated;
    public Integer isTransacted;
    public Integer optedOut;
    public Integer isBlocked;
    public Object blockReason;
}
