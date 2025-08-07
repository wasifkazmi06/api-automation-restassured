package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreSensitiveInfoPojo {
    public String responseStatus;
    public String failureCode;
    public String message;
    public String refKey;
}
