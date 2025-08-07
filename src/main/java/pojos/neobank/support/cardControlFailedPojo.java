package pojos.neobank.support;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class cardControlFailedPojo {
    public String timestamp;

    public int status;

    public String error;

    public String message;

    public String path;

    public String errorCode;
}
