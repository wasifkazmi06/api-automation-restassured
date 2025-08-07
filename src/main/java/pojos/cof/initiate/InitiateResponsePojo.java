package pojos.cof.initiate;
import java.sql.Timestamp;

public class InitiateResponsePojo {
    public String redirectUrl;
    public String token;
    public int status;
    public Timestamp timestamp;
    public String error;
    public String message;
    public String path;
    public String errorCode;
}
