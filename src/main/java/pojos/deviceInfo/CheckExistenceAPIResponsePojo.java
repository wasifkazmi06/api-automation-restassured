package pojos.deviceInfo;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class CheckExistenceAPIResponsePojo {
    public boolean isDevicePresent;
    public Map<String, Object> deviceFetchDetails;
}