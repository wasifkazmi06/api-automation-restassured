package pojos.platform.getUserData;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserD30Details {
    private boolean active;
    private Date startDate;
    private Date expiryDate;
    private String userState;

}
