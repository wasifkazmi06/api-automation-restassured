package pojos.devicebinding;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceBindingPojo {
    public String smsToken;
    public String smsTokenId;
    public String[] recipientNumber;
    public String bindingStatus;
    public String bindingId;


    //Error response
    public String code;
    public String description;
    public String details;
    public String id;
    public String message;
    public String status;
    public String timestamp;

}
