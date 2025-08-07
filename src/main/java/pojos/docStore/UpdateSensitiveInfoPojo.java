package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSensitiveInfoPojo {
    public String responseStatus;
    public Object failureCode;
    public Object message;
}
