package pojos.ams;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class userUuid {
    public String firstName;
    public String lastName;
    public String uuid;
    public Object email;
    public String mobile;
    public String verifiedMobile;
    public Integer deleted;
    public Integer maxLoginAttempts;

}
