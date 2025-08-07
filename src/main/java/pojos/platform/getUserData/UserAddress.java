package pojos.platform.getUserData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAddress {
    private String addressline1;
    private Object addressline2;
    private Object city;
    private Object state;
    private Object country;
    private String zipCode;
    private String alias;
    private Boolean verified;
    private String source;
}
