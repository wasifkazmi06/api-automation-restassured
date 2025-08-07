package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitCaFormRequestPojo {
    public String city;
    public String houseNo;
    public String landmark;
    public String pinCode;
    public String residenceType;
    public String state;
    public String street;
}
