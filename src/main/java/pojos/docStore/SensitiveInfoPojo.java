package pojos.docStore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensitiveInfoPojo {
    public String refKey;
    public String uuid;
    public String userId;
    public String type;
    public Object metaData;
    public String status;
    public String sensitiveInfo;
}
