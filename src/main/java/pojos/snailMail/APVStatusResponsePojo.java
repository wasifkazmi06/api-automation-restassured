package pojos.snailMail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class APVStatusResponsePojo {

    private long timestamp;
    private String uuid;
    private String status;
    private String error;
    private String message;
    private String path;
    private String errorCode;
    private Object metadata;
    private SnailMailDataPojo data;
    private Object errorMsg;

}