package pojos.docStore;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DeleteSensitiveInfoPojo {
    public String responseStatus;
    public Object failureCode;
    public String message;
}
