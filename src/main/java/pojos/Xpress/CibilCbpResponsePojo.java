package pojos.Xpress;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CibilCbpResponsePojo {

    public static Object bureauResponse;
    public long nextRepullDate;
    public String userBureauState;
    public long cachedAt;
    public String pan;
    public Long timestamp;
    public Integer status;
    public String error;
    public String message;
    public String path;
}
