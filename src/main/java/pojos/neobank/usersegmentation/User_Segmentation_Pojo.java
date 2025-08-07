package pojos.neobank.usersegmentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User_Segmentation_Pojo {

    public Payload payload;
    public String message;
    public String status;

}
