package pojos.chatbot;

import lombok.Getter;
import lombok.Setter;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import pojos.heimdall.Data;

@Getter
@Setter
public class GetInfoPojo {
    public String requestId;
    public String statusCode;
    public DataPojo data;

    /**
     * Error
     * */

    public String message;
}
