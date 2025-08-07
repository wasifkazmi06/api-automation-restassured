package pojos.neobank.usersegmentation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Get_All_Active_Segments_Pojo {
    public Payload[] payload;
    public String message;
    public String status;
}
