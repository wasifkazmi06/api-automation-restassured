package pojos.neobank.dcs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import pojos.neobank.dcs.payload.Payload;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DCS_Pojo {

    public Payload payload;
    public String statusCode;
    public String status;
    public String message;
}
