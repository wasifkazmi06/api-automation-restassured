package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class bbpsCallBackResponse {
    //Error Message Response
    public String timestamp;
    public Integer status;
    public String error;
    public String message;
    public String path;

}
