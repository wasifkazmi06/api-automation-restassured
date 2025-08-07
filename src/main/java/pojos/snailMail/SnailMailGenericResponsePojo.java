package pojos.snailMail;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
@Getter
@Setter
public class SnailMailGenericResponsePojo {
    //HEIMDALL PULL/PUSH RESPONSE
    private String requestId;
    private String status;
    private ArrayList<SnailMailDataPojo> data;
    private Object errorCode;
    private Object errorMsg;

}
