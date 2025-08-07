package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileData {
    public String email;
    public String emailVerified;
    public Object emailVerifiedDate;
    public String mobile;
    public String mobileVerified;
    public Long mobileVerifiedDate;
    public String firstName;
    public String lastName;
    public String uuid;
    public String tenantId;

    public String internalCode;
    public String internalMessage;
    public String endUserMessage;
}
