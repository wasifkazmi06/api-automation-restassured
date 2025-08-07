package pojos.ams;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class validateUserPojo {
    public String responseCode;
    public ResponseData responseData;


    //Error Case
    public String timestamp;
    public String status;
    public String error;
    public String message;
    public String path;
    public String internalCode;
    public String internalMessage;
    public String endUserMessage;

    public String type;
    public String title;
    public String detail;
    public String instance;
    public fieldErrors[] fieldErrors;







}
