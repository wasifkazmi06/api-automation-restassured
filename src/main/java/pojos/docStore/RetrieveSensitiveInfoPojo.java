package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class RetrieveSensitiveInfoPojo {
    public String responseStatus;
    public Object failureCode;
    public Object message;
    public ArrayList<SensitiveInfoPojo> sensitiveInfoEntryResponses;
}
