package pojos.cof.eligibility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDetailsPojo {
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobile;
    private String pan;
    private String dob;
    private String gender;
    private String email;
    private AddressPojo address;

}
