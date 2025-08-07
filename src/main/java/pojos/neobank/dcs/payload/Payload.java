package pojos.neobank.dcs.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payload {

    public String verticalId;
    public String verticalName;
    public String clientId;
    public String clientName;
    public String vertical;
    public String clientStatus;
    public String key;
    public String value;
    public String configStatus;
    public String deactivateAt;

}
