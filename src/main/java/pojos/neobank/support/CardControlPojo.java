package pojos.neobank.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardControlPojo {

    public Object exception;
    public Object pagination;
    public String result;

    // Response for the Failed case
    public String timestamp;
    public int status;
    public String error;
    public String message;
    public String path;
    public String errorCode;
}
