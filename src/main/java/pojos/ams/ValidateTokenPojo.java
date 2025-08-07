package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateTokenPojo {


    //Success
    public String uuid;
    public String email;
    public String mobile;
    public String created;
    public String firstName;
    public String lastName;
    public String verifiedEmail;
    public String verifiedMobile;
    public String expiration;
    public String clientId;
    public String scopes;
    public String roles;
    public String implicitType;


    //Error
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;


}


